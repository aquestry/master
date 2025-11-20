package dev.anton.core

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient
import com.github.dockerjava.transport.DockerHttpClient
import dev.anton.model.Server
import dev.anton.model.Template
import dev.anton.logger
import java.nio.file.Files
import java.nio.file.Paths

object ServerManager {

    val host = System.getenv("DOCKER_HOST")
        ?: if (System.getProperty("os.name").contains("win", true)) "tcp://localhost:2375"
        else if (Files.exists(Paths.get("/var/run/docker.sock"))) "unix:///var/run/docker.sock"
        else "tcp://host.docker.internal:2375"

    private val config = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost(host).build()
    var httpClient: DockerHttpClient = ApacheDockerHttpClient.Builder().dockerHost(config.dockerHost).build()
    var dockerClient: DockerClient = DockerClientImpl.getInstance(config, httpClient)

    @Suppress("DEPRECATION")
    fun createServer(template: Template): Server {
        val containerPort = ExposedPort.tcp(25565)

        val container = dockerClient.createContainerCmd(template.dockerImage)
            .withExposedPorts(containerPort)
            .withPublishAllPorts(true)
            .exec()

        dockerClient.startContainerCmd(container.id).exec()

        val containers = dockerClient.listContainersCmd()
            .withShowAll(true)
            .withIdFilter(listOf(container.id))
            .exec()

        require(containers.isNotEmpty()) { "Container not found: ${container.id}" }

        val c = containers.first()

        val port = c.ports
            ?.firstOrNull { it.privatePort == 25565 && it.type.equals("tcp", ignoreCase = true) }
            ?.publicPort
            ?: error("No host port assigned (containerPorts=${c.ports?.toList()})")

        logger.info("Created new server on host port $port, using template ${template.name}.")

        val server = Server(container.id, template, "localhost", port)
        ServerRegistry.add(server)
        return server
    }
}

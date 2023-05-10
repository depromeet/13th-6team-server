import org.springframework.boot.gradle.tasks.bundling.BootJar


dependencies{
    api ("io.github.openfeign:feign-httpclient:12.1")
    api ("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.4")
    api ("io.github.openfeign:feign-jackson:12.1")
}


tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
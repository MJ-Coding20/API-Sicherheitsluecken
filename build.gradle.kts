plugins {
    id("java")
}

group = "de.seminar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.1.0")
    implementation("org.glassfish.jersey.core:jersey-server:3.1.8")
    implementation("org.glassfish.jersey.core:jersey-common:3.1.8")
    implementation("org.glassfish.jersey.inject:jersey-hk2:3.1.8")
    implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-http:3.1.8")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:3.1.8")
    implementation("com.fasterxml.jackson.core:jackson-core:2.17.1")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    implementation("com.itextpdf:itext7-core:7.1.16")
    implementation("com.google.code.gson:gson:2.13.1")
}

tasks.jar {
    doFirst {
        from(configurations.runtimeClasspath.get().map { if(it.isDirectory()) it else zipTree(it) })
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes.put("Main-Class", "de.seminar.ServerApp")
    }

    exclude("META-INF/*.RSA", "META-INF/*.SF","META-INF/*.DSA")
}

tasks.test {
    useJUnitPlatform()
}
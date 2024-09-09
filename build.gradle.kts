plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    FastCGI lib
    implementation(fileTree("libs"));

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}


tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.example.Main" // Устанавливаем главный класс для JAR
    }

    // Включаем зависимости в JAR файл
    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE // Избегаем дублирования ресурсов\
    archiveFileName.set("webLab1.jar") // Задаем имя файла
}
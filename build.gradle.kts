import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import nu.studer.gradle.jooq.JooqEdition

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    id("org.springframework.boot") version "2.6.2"
    id("nu.studer.jooq") version "5.2.1"
    id("org.ajoberstar.grgit") version "4.1.1"
}

apply(plugin = "io.spring.dependency-management")

group = "com.translator"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8
repositories {
    mavenCentral()
}

dependencies {
    api("org.springframework.boot:spring-boot-starter")
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-jooq")
    api("org.thymeleaf:thymeleaf-spring5:3.0.14.RELEASE")
    api("org.thymeleaf.extras:thymeleaf-extras-java8time:3.0.4.RELEASE")
    api("org.thymeleaf:thymeleaf:3.0.14.RELEASE")
    implementation("mysql:mysql-connector-java")
    testRuntimeOnly("mysql:mysql-connector-java")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test"))
    //testImplementation("org.testcontainers:mysql")
    jooqGenerator("mysql:mysql-connector-java")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.3.8.RELEASE")
    implementation("org.springframework.security:spring-security-jwt:1.1.1.RELEASE")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

//dependencyLocking {
//	lockAllConfigurations()
//	lockMode.set(LockMode.STRICT)
//}

jooq {
    version.set("3.14.8")
    edition.set(JooqEdition.OSS)

    configurations {
        create("translator") {
            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc = jdbc.apply {
                    driver = "com.mysql.cj.jdbc.Driver"
                    url = "jdbc:mysql://127.0.0.1:3306"
                    username = "root"
                    password = "root"
                }
                generator = generator.apply {
                    name = "org.jooq.codegen.JavaGenerator"
                    database.apply {
                        name = "org.jooq.meta.mysql.MySQLDatabase"
                        inputSchema = "translator"
                    }
                    target.apply {
                        packageName = "org.jooq.translator"
                        directory = "src/main/java"
                    }
                }
            }
        }
    }
}
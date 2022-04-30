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
	runtimeOnly("mysql:mysql-connector-java")
	testRuntimeOnly("mysql:mysql-connector-java")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(kotlin("test"))
	testImplementation("org.testcontainers:mysql")
	jooqGenerator("mysql:mysql-connector-java")
}

tasks.test {
	useJUnit()
}

tasks.withType<KotlinCompile>() {
	kotlinOptions.jvmTarget = "1.8"
}

dependencyLocking {
	lockAllConfigurations()
	lockMode.set(LockMode.STRICT)
}

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
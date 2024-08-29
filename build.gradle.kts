abstract class ProducerTask : DefaultTask() {

    @get:OutputFile
    abstract val producedFile: RegularFileProperty

    @TaskAction
    fun createFile() {
        producedFile.asFile.get().writeText("Hello from producer!")
    }
}

abstract class ConsumerTask : DefaultTask() {

    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:NormalizeLineEndings
    @get:Optional
    abstract val producedFile: RegularFileProperty

    @get:Input
    val consumedString: Provider<String> = producedFile.map { it.asFile.readText() }

    @TaskAction
    fun createFile() {
        println(consumedString.get())
    }
}

val producerTask by project.tasks.registering(ProducerTask::class) {
    producedFile.set(layout.buildDirectory.file(project.file("producer.txt").absolutePath))
}

val consumerTask by project.tasks.creating(ConsumerTask::class) {
    producedFile.set(producerTask.flatMap { it.producedFile })
}
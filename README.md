### Description

This project demonstrates the problem
with [configuration cache](https://docs.gradle.org/current/userguide/configuration_cache.html)
and lazily configured task inputs.

### How to reproduce

Run the command:

```bash
./gradlew consumerTask 
```

It will fail with error:

```
/{path_to_project}/producer.txt (No such file or directory)
```

### Without configuration cache project feature

If we disable the [configuration cache](https://docs.gradle.org/current/userguide/configuration_cache.html)
in [gradle.properties](gradle.properties),
then everything works fine. 
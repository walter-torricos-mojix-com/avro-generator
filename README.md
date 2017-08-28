# avro-generator

## Steps add this plugin:
1. Clone the repo.
2. Publish the plugin locally by running "$ sbt compile publishLocal" this way it will be published to {user.home}/.ivy2/local.
3. Add the plugin in your target project by adding the following line:    
addSbtPlugin("local" % "avro-generator" % "0.0.1")    
to the file project/plugins.sbt


## Usage
Once you complete the steps to add the plugin you will be able to use it by running it like this from the root folder of the project:
    $ sbt "avro complete.class.Name /path/to/output"

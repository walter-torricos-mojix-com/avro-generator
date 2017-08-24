# avro-generator

To be able to use this plugin first publish it locally with:
    $ sbt publishLocal
This way it will be published to {user.home}/.ivy2/local.

Now you can add it as a plugin like any other plugin by adding it to the plugins.sbt file:
addSbtPlugin("local" % "avro-generator" % "0.0.1")

try with a local resolver in case of problems
resolvers += Resolver.sonatypeRepo("local")
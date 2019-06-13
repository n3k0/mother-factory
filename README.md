# This is the mother factory plugin.
When you cannot use something like **Podam factory**, or don't want to learn about a runtime pojo filler tool, or your server blows up any time you try to build yor project because limited resources, you can try this plugin, so you will end with some additional java files that returns objects previously filled properties:

    public class TheFactory {
      public Pojo createPojo() {
        Pojo pojo = new Pojo();
        pojo.setString("vGhQbUvGkU");
        pojo.setInteger(120);
        pojo.setDate(new Date(1607024887475L));
        pojo.setTimestamp(new Timestamp(-1827973745262L));
        pojo.setLong(-1023599601022558846L);
        pojo.setChar('ᇙ');
        pojo.setBigDecimal(new BigDecimal("9.75877134285659"));
        pojo.setShort(Short.valueOf("6416"));
        pojo.setDouble(3.398196599071804);
        pojo.setFloat(5.413991f);
        pojo.setByte((byte)71);
        pojo.setList(Arrays.asList("asdq3qdskmas990"));
        pojo.setInterfaze((AnInterface)null);
        pojo.setEnum(CustomEnum.ENUM_VALUE);
        return pojo;
      }

## Some interesting points.

 1. Properties that are interfaces are not filled, just a null value with a cast marker.
 2. There isn´t number two.
 2. The plugin can detect and fill primitive and object fields (long->Long).
 4. There isn't number four.

Just have to do some steps.
Add the corresponding `<plugin>` tag in the `<plugins>` section, like this:

	<plugin>
    	<groupId>net.psybit.factory</groupId>
    	<artifactId>mother-factory</artifactId>
    	<version>0.5.1</version>
    	<configuration>
    		<testSourceDirectory>optional, default value is src/test/java, this is the directory where mother factory will put the factories made</testSourceDirectory>
    		<outputDirectory>optional, default value is target/classes, this is the directory where mother-factory will look for classes that you want to use to do the factories</outputDirectory>
    		<pojoPackages> pojo packages, separated by <pojoPackage> tags, optional </pojoPackages>
    		<factoryQualifiedNames>Factory names, separated by <factoryQualifiedName> tags  if is more than one</factoryQualifiedNames>
    		<fileFilterInclusions>criteria, separated by <fileFilterInclusion> tag each criteria </fileFilterExclusions>
    		<fileFilterExclusions>criteria, separated by <fileFilterExclusion> tag each criteria </fileFilterExclusions>
    		<staticMethods> true|false, depending if the factory is required to have static methods, default is false</staticMethods>
    		<canIHazFidder> true|false, depending, if the factory is required to have the factory for the random values in its code, default is false</canIHazFidder>
			<simpleFidderInstanceName>name of the simple fidder singleton, IF the <canIHazFidder> is true, default alue is INSTANCE</simpleFidderInstanceName>
			<simpleFidderCreateMethodName>name of the object creation method for the simpleFidder instance, IF the <canIHazFidder> is true, default value is create</simpleFidderCreateMethodName>
			<stringsLength>Length of the string properties, default 100</stringsLength>
			<integerLimit>Integer limit for the random integer generator, default 10</integerLimit>
			<floatPointFromLimit>Lower float number limit for the random float/double generator, default 9.5</floatPointFromLimit>
			<floatPointToLimit>Higher float number limit for the random float/double generator, default 100.3</floatPointToLimit>
			<dataStructureSize>Number of elements that will be set in each collection property, default 2</dataStructureSize>
    		</configuration>
	</plugin>



For the following parameters, the example values will be:

> `com.company.application.external.pojo`

> `com.company.application.internal.entities`



- **pojoPackages**: Taking the last example, the pojo packages should be external.pojo, and internal.entities, separated by **pojoPackage** tag, just as:

        <pojoPackages>
            <pojoPackage>com.company.application.external.pojo</pojoPackage>
    	    <pojoPackage>com.company.application.internal.entities</pojoPackage>
        </pojoPackages>`

- **factoryQualifiedNames**: This parameter indicates the target qualified name of the factories:

	    <factoryQualifiedNames>
		    <factoryQualifiedName>com.company.application.external.ExternalFactory</factoryQualifiedName>
		    <factoryQualifiedName>com.company.application.internal.InternalFactory</factoryQualifiedName>
	    </factoryQualifiedNames>


- **fileFilterInclusions**: This parameter can be filled with some criteria, following the special characters **^** and **$** like a regular expression, if no special character is added, the exact name will be searched.

Additional note, if you want to include some package, the name should be the complete package, it does not fall into the regex conditions:

        <fileFilterInclusions>
		    <fileFilterInclusion>ExactNameCriteria.class</fileFilterInclusion>
		    <fileFilterInclusion>this.is.just.a.package</fileFilterInclusion>
		    <fileFilterInclusion>EndsWithThis.class$</fileFilterInclusion>
		    <fileFilterInclusion>^StartsWithThis</fileFilterInclusion>
	    </fileFilterInclusions>

- **fileFilterExclusions**: Such the **fileFilterInclusions** tag, just the inverse operation, if you want to avoid an specific class from a package previously included, you can do it here:

        <fileFilterExclusions>
        	<fileFilterInclusion>this.is.just.an.excluded.package</fileFilterInclusion>
		    <fileFilterExclusion>ClassFromTheIncludedPackage.class</fileFilterExclusion>
		    <fileFilterInclusion>EndsWithThis.class$</fileFilterInclusion>
		    <fileFilterInclusion>^StartsWithThis</fileFilterInclusion>
		    <fileFilterExclusion>.json</fileFilterExclusion>
		    <fileFilterExclusion>.txt</fileFilterExclusion>
	    </fileFilterExclusions>

- **canIHazFidder**: This parameter asks for an additional little file, named SimpleFidder.java that will be added on the root package, this file will fill the fields of the pojos with a random value in runtime if true is set, if the value is set to false, the value will be filled when the factory is generated.
Example.
When value is true:

      private static final SimpleFidder INSTANCE = SimpleFidder.instance();
        public Pojo createPojo() {
            Pojo pojo = new Pojo();
            pojo.setString(instance.create(String.class));
            return pojo;
        }

If the value is false,  the result will be:

     public Pojo createPojo() {
                Pojo pojo = new Pojo();
                pojo.setString("kGtAdGfQxR");
                return pojo;
            }


- **staticMethods**: If this parameter is set to a true value, an `static` modifier will be added to each method signature, in order to provide a factory with static methods. Note: Just if *canIHazFidder* is set as true.

- **simpleFidderInstanceName**: This parameter can be set with an optional name for the SimpleFidder instance that will be added in each class, the default value is *INSTANCE*. Note: Just if *canIHazFidder* is set as true.

- **simpleFidderCreateMethodName**: Don´t you like the *create* method name in the SimpleFidder class? If a value is set to this parameter, the name of the *create* method will be changed. Note: Just if *canIHazFidder* is set as true.

** I hope the last parameters `stringsLength, integerLimit, floatPointFromLimit, floatPointToLimit, dataStructureSize` are self explanatory ;-)

As you can see, all of the configuration tags are optional, you can set the plugin like this:

	<plugin>
    	<groupId>net.psybit.factory</groupId>
    	<artifactId>mother-factory</artifactId>
    	<version>0.5.1</version>
    </plugin>
    
And you will have a default factory, but if you found that not all of the objects are included, maybe you'll have to try 
putting on the debug flag in maven (-X) in order to find the reason.

Finally, the changes will be reflected as follows, in the `${testSourceDirectory}` folder:

<p align="center">
  <img src="https://i.imgur.com/KixLfPg.png" />
</p>


This plugin has been tested with:

 - Maven 3.5.2
 - Jdk 1.7
 - Jdk 1.8.


### **Made with     

<p align="center">
  <img src="https://i.imgur.com/bEqxO1y.png" />
</p>

By: **
## The **Psybit** core team:


n3k0 - ![*@n3k0_mx*](https://twitter.com/n3k0_mx)

morsetvite - ![*@AlejandroMZDZ*](https://twitter.com/AlejandroMZDZ)

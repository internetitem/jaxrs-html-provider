jaxrs-html-provider
===================

HTML Generation for JAX-RS applications

## Usage ##

Add the following to your `pom.xml` (updating `<version>` where appropriate):

    <dependency>
        <groupId>com.internetitem.web</groupId>
        <artifactId>jaxrs-html-provider</artifactId>
        <version>1.0.0</version>
    </dependency>

You must provide your own JAX-RS and FreeMarker dependencies.

The class `JaxRsHtmlProvider` contains the provider logic, and will by default load templates from /templates on the classpath.

Within your service methods, you need to do two things:

 * Specify `@Produces("text/html")`
 * Configure the service/model's template filename

The template's filename can be specified using one of the following methods (the first one found will be used):

 * Implement the `TemplateAware` interface on the model class
 * Annotate the service method with `@TemplateName`
 * Annotate the model class with `@TemplateName`

### Spring Boot ###

If you are using Spring Boot, the Configuration class `JaxRsHtmlProviderConfiguration` will automatically add two beans:

 * `jaxRsHtmlProvider` - The actual `JaxRsHtmlProvider` which is a JAX-RS @Provider
 * `freemarkerHtmlGenerator` - The default implementation of `Renderer` which uses Freemarker to generate HTML. If you provide a different imlementation of `Renderer`, that one will be used instead

## Dependencies ##

You must declare JAX-RS and FreeMarker as dependencies. If you want Spring Boot support, you must bring declare that yourself too.

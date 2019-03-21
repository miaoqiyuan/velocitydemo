package com.my.study;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class App {

    public static void main(String[] args) throws IOException {
        createMavenStruct("d:/sv/", "test");
    }

    public static void createMavenStruct(String baseDir, String prjName) throws IOException {
        baseDir = baseDir + prjName + "/";
        FileUtil.createDir(baseDir);
        FileUtil.createDir(baseDir + Constant.JAVA_DIR);
        FileUtil.createDir(baseDir + Constant.SOURCES_DIR);
        FileUtil.createDir(baseDir + Constant.PACKAGE);
        VelocityContext context = new VelocityContext();
        context.put("prj_name", prjName);
        createVelocity("pom.xml.vm", baseDir + "pom.xml", context);
    }

    public static void createVelocity(String tempName, String path, VelocityContext ctx) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template modifyTpt = ve.getTemplate(tempName);
        merge(modifyTpt, ctx, path);
    }

    public static void merge(Template template, VelocityContext ctx, String path) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path, "GBK");
            template.merge(ctx, writer);
            writer.flush();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}

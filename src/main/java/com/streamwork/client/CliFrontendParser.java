package com.streamwork.client;

import org.apache.commons.cli.*;

public class CliFrontendParser {
    // 添加帮助选项
    static Option HELP_OPTION = new Option("h", "help", false, "显示帮助信息");
    // 添加 JAR 包路径选项
    static Option JAR_OPTION = new Option("j", "jar", true, "指定 JAR 包路径");
    // 添加类名选项
    static Option CLASS_OPTION = new Option("c", "class", true, "指定主类名");
    // 添加并行度选项
    static Option PARALLELISM_OPTION = new Option("p", "parallelism", true, "设置并行度");

    static {
        // 将所有选项设置为必填项
        JAR_OPTION.setRequired(true);
        CLASS_OPTION.setRequired(true);
        PARALLELISM_OPTION.setRequired(true);
    }
    // 获取运行配置项
    public static Options getRunCommandOptions() {

        Options options = new Options();
        options.addOption(HELP_OPTION)
                .addOption(JAR_OPTION)
                .addOption(CLASS_OPTION)
                .addOption(PARALLELISM_OPTION);

        return options;
    }
    // 命令行解析
    public static CommandLine parse(Options options, String[] args) throws ParseException {
        try {
            return new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            System.err.println("参数解析失败: " + e.getMessage());
            printHelp(options);
            throw e; // 抛出异常由调用方处理
        }
    }

    // 打印help
    public static void printHelp(Options options) {
        new HelpFormatter().printHelp("StreamWork", options, true);
    }

}

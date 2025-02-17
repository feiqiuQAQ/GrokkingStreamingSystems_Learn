package com.streamwork.client;

import com.streamwork.ch03.api.Job;
import com.streamwork.ch03.engine.JobStarter;
import com.streamwork.ch03.job.JobLoader;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;
/*
*
* 命令行处理参数解析和流程控制
* */

public class CliFrontend {

    // actions  用户命令行执行的命令 例如 flink run
    private static final String ACTION_START = "start";
    private static final String ACTION_STOP = "stop";

    public static void main(String[] args) {
        CliFrontend cliFrontend = new CliFrontend();
        int return_num = cliFrontend.parseAndRun(args);

    }
    // 解析命令行参数并运行
    public int parseAndRun(String[] args) {

        // 检查行为
        if (args.length < 1) {
            System.out.println("Please specify an action.");
            return 1;
        }

        // 获取行为
        String action = args[0];
        // 从命令行中移除行为参数
        final String[] params = Arrays.copyOfRange(args, 1, args.length);

        try {
            // do action
            switch (action) {
                case ACTION_START:
                    System.out.println("run");
                    run(params);
                    return 0;
                case ACTION_STOP:
                    stop(params);
                    return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    // 执行start行为
    private int run(String[] params) throws Exception {

        /*TODO 获取run动作，默认的配置项*/
        final Options commandOptions = CliFrontendParser.getRunCommandOptions();
        try {
            CommandLine cmd = CliFrontendParser.parse(commandOptions, params);

            if (cmd.hasOption("h")) {
                CliFrontendParser.printHelp(commandOptions);
                return 0;
            }

            String jarPath = cmd.getOptionValue("j");
            String className = cmd.getOptionValue("c");
            int parallelism = Integer.parseInt(cmd.getOptionValue("p"));

            System.out.println("启动任务:");
            System.out.println("JAR 路径: " + jarPath);
            System.out.println("主类名: " + className);
            System.out.println("并行度: " + parallelism);



           // 解析jar包加载用户代码
            JobLoader loader = new JobLoader(jarPath);
            // 调用用户 main 方法并传递并行度作为参数
            loader.runMain(className, new String[]{ String.valueOf(parallelism) });
            loader.close();

            return 0;
        } catch (ParseException e) {
            return 1; // 错误信息已在解析时打印
        } catch (NumberFormatException e) {
            System.err.println("并行度参数必须为整数");
            return 1;
        }


    }
    // 启动job
    private void startJob(Job job) {
        JobStarter starter = new JobStarter(job);
        starter.start();
        System.out.println("Job started successfully");
    }

    private void stop(String[] params) {
    }

}

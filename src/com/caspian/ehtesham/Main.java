package com.caspian.ehtesham;

/**
 * Created by ehtesham on 2016/12/18.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Process process = null;
        String processName = args[0];
        String serverIp = args[1];
        String serverPort = args[2];
        String interval = args[3];
        if (!processName.equals(null))
            process = Operation.runProcess(processName);
        while (true) {
            HeartBeat heartBeat = Operation.getSystemInfo(process, processName);
            Operation.saveXml(heartBeat);
            if (!interval.equals(null))
                Thread.sleep(Long.parseLong(interval) * 1000);
            if (serverIp.equals(null) && serverPort.equals(null))
                Operation.sendStatus(serverIp, Integer.parseInt(serverPort));
            else
                Operation.sendStatus(Operation.getProperty("IP"), Integer.parseInt(Operation.getProperty("PORT")));
        }
    }
}

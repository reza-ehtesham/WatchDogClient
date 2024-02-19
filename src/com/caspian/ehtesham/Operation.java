package com.caspian.ehtesham;

import com.sun.istack.internal.logging.Logger;
import com.sun.management.OperatingSystemMXBean;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.time.LocalTime;
import java.util.Properties;
import java.util.logging.Level;

import static java.lang.management.ManagementFactory.getOperatingSystemMXBean;

/**
 * Created by ehtesham on 2016/12/18.
 */
public class Operation {
    private static final String DEFAULT_PROCESS_NAME = "machine";
    private static final Logger logger = Logger.getLogger(Operation.class);


    /**
     Get System Information Client & Set On HeartBeat Entity
    * */
        public static HeartBeat getSystemInfo(Process process, String processName) throws Exception {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.setProcessoresCount(getOperatingSystemMXBean().getAvailableProcessors());
        heartBeat.setTotalMemory(((OperatingSystemMXBean) getOperatingSystemMXBean()).getTotalPhysicalMemorySize());
        heartBeat.setFreeMemory(((OperatingSystemMXBean) getOperatingSystemMXBean()).getFreePhysicalMemorySize());
        heartBeat.setSystemCpuLoad(((OperatingSystemMXBean) getOperatingSystemMXBean()).getSystemCpuLoad());
        heartBeat.setTime(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute());
        heartBeat.setAlive(process.isAlive());
        heartBeat.setProcessName(processName);
        logger.log(Level.INFO, "Collecting Heart Beat System Information");
        return heartBeat;
    }

    /**
     *
     * @param processName
     * Get Process Name & Run Process
     * @throws IOException
     */
    public static Process runProcess(String processName) throws IOException {
        if (processName.equals(DEFAULT_PROCESS_NAME))
            return null;
        return Runtime.getRuntime().exec("cmd /c" + processName);
    }

    /**
     *
     * @param heartBeat
     * Save & Marshal HeartBeatXml
     */
    public static void saveXml(HeartBeat heartBeat) {
        try {
            OutputStream outputStream = new FileOutputStream(getProperty("HeartBeatFileName") +
                    getProperty("HeartBeatFileFormat"));
            JAXBContext heartBeatXML = JAXBContext.newInstance(HeartBeat.class);
            Marshaller marshaller = heartBeatXML.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            logger.log(Level.INFO, "Create XML File");
            marshaller.marshal(heartBeat, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param ip
     * @param port
     * @throws Exception
     * Send Client Information On Socket To Server
     */
    public static void sendStatus(String ip, Integer port) throws Exception {
        try {
            Socket socket = new Socket(ip, port);
            OutputStream outputStream = socket.getOutputStream();
            Files.copy(new File(getProperty("HeartBeatFileName") +
                    getProperty("HeartBeatFileFormat")).toPath(), outputStream);
            logger.log(Level.INFO, "Send XML File");
            outputStream.close();
            socket.close();
            logger.log(Level.FINE, "File transfer complete");
        } catch (Exception e) {

        }
    }
/*
Config Property File
 */
    public static String getProperty(String keyProperty) throws Exception {
        Properties configProperties = new Properties();
        configProperties.load(new FileInputStream("NetworkConfig.properties"));
        return configProperties.getProperty(keyProperty);
    }
}
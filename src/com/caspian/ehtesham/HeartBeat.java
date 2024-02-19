package com.caspian.ehtesham;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ehtesham on 2016/12/18.
 */
@XmlRootElement
public class HeartBeat {
    private String time;
    private long totalMemory;
    private long freeMemory;
    private double systemCpuLoad;
    private String processName;
    private boolean isAlive;
    private int processoresCount;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @XmlElement(name = "total-memory")
    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    @XmlElement(name = "free-memory")
    public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    @XmlElement(name = "system-cpu-load")
    public double getSystemCpuLoad() {
        return systemCpuLoad;
    }

    public void setSystemCpuLoad(double systemCpuLoad) {
        this.systemCpuLoad = systemCpuLoad;
    }

    @XmlElement(name = "process-name")
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @XmlElement(name = "process-is-alive")
    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getProcessoresCount() {
        return processoresCount;
    }

    public void setProcessoresCount(int processoresCount) {
        this.processoresCount = processoresCount;
    }
}

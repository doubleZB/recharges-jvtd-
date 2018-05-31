/**
 * IDispatchControlLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.huateng.haobai.ppcore.control;

public class IDispatchControlLocator extends org.apache.axis.client.Service implements com.huateng.haobai.ppcore.control.IDispatchControl {

    public IDispatchControlLocator() {
    }


    public IDispatchControlLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IDispatchControlLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IDispatchControlHttpPort
    private String IDispatchControlHttpPort_address = "http://flowpackage.bestpay.com.cn/flowpackage/services/businessService";

    public String getIDispatchControlHttpPortAddress() {
        return IDispatchControlHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private String IDispatchControlHttpPortWSDDServiceName = "IDispatchControlHttpPort";

    public String getIDispatchControlHttpPortWSDDServiceName() {
        return IDispatchControlHttpPortWSDDServiceName;
    }

    public void setIDispatchControlHttpPortWSDDServiceName(String name) {
        IDispatchControlHttpPortWSDDServiceName = name;
    }

    public com.huateng.haobai.ppcore.control.IDispatchControlPortType getIDispatchControlHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IDispatchControlHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIDispatchControlHttpPort(endpoint);
    }

    public com.huateng.haobai.ppcore.control.IDispatchControlPortType getIDispatchControlHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.huateng.haobai.ppcore.control.IDispatchControlHttpBindingStub _stub = new com.huateng.haobai.ppcore.control.IDispatchControlHttpBindingStub(portAddress, this);
            _stub.setPortName(getIDispatchControlHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIDispatchControlHttpPortEndpointAddress(String address) {
        IDispatchControlHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.huateng.haobai.ppcore.control.IDispatchControlPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.huateng.haobai.ppcore.control.IDispatchControlHttpBindingStub _stub = new com.huateng.haobai.ppcore.control.IDispatchControlHttpBindingStub(new java.net.URL(IDispatchControlHttpPort_address), this);
                _stub.setPortName(getIDispatchControlHttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("IDispatchControlHttpPort".equals(inputPortName)) {
            return getIDispatchControlHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://control.ppcore.haobai.huateng.com", "IDispatchControl");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://control.ppcore.haobai.huateng.com", "IDispatchControlHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("IDispatchControlHttpPort".equals(portName)) {
            setIDispatchControlHttpPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

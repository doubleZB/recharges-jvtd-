package com.huateng.haobai.ppcore.control;

public class IDispatchControlPortTypeProxy implements com.huateng.haobai.ppcore.control.IDispatchControlPortType {
  private String _endpoint = null;
  private com.huateng.haobai.ppcore.control.IDispatchControlPortType iDispatchControlPortType = null;
  
  public IDispatchControlPortTypeProxy() {
    _initIDispatchControlPortTypeProxy();
  }
  
  public IDispatchControlPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initIDispatchControlPortTypeProxy();
  }
  
  private void _initIDispatchControlPortTypeProxy() {
    try {
      iDispatchControlPortType = (new com.huateng.haobai.ppcore.control.IDispatchControlLocator()).getIDispatchControlHttpPort();
      if (iDispatchControlPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iDispatchControlPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iDispatchControlPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iDispatchControlPortType != null)
      ((javax.xml.rpc.Stub)iDispatchControlPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.huateng.haobai.ppcore.control.IDispatchControlPortType getIDispatchControlPortType() {
    if (iDispatchControlPortType == null)
      _initIDispatchControlPortTypeProxy();
    return iDispatchControlPortType;
  }
  
  public String dispatchCommand(String in0, String in1) throws java.rmi.RemoteException{
    if (iDispatchControlPortType == null)
      _initIDispatchControlPortTypeProxy();
    return iDispatchControlPortType.dispatchCommand(in0, in1);
  }
  
  
}
%====================================================================================
% carparking description   
%====================================================================================
context(ctxcarparking, "localhost",  "TCP", "8100").
context(ctxfan, "127.0.0.1",  "TCP", "8001").
context(ctxweightsensor, "127.0.0.1",  "TCP", "8002").
context(ctxthermometer, "127.0.0.1",  "TCP", "8003").
context(ctxoutsonar, "127.0.0.1",  "TCP", "8004").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( fanactor, ctxfan, "external").
  qactor( weightsensoractor, ctxweightsensor, "external").
  qactor( thermometeractor, ctxthermometer, "external").
  qactor( outsonaractor, ctxoutsonar, "external").
  qactor( basicrobot, ctxbasicrobot, "external").
  qactor( parkingmanagerservice, ctxcarparking, "it.unibo.parkingmanagerservice.Parkingmanagerservice").
  qactor( itoccactor, ctxcarparking, "it.unibo.itoccactor.Itoccactor").
  qactor( dtfreeactor, ctxcarparking, "it.unibo.dtfreeactor.Dtfreeactor").
  qactor( parkingservicestatusguiactor, ctxcarparking, "it.unibo.parkingservicestatusguiactor.Parkingservicestatusguiactor").
  qactor( temperatureactor, ctxcarparking, "it.unibo.temperatureactor.Temperatureactor").
  qactor( trolleyactor, ctxcarparking, "it.unibo.trolleyactor.Trolleyactor").

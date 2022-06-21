%====================================================================================
% devices description   
%====================================================================================
context(ctxfan, "localhost",  "TCP", "8001").
context(ctxweightsensor, "localhost",  "TCP", "8002").
context(ctxthermometer, "localhost",  "TCP", "8003").
context(ctxoutsonar, "localhost",  "TCP", "8004").
context(ctxcarparking, "127.0.0.1",  "TCP", "8100").
 qactor( fanactor, ctxfan, "it.unibo.fanactor.Fanactor").
  qactor( weightsensoractor, ctxweightsensor, "it.unibo.weightsensoractor.Weightsensoractor").
  qactor( thermometeractor, ctxthermometer, "it.unibo.thermometeractor.Thermometeractor").
  qactor( outsonaractor, ctxoutsonar, "it.unibo.outsonaractor.Outsonaractor").

%====================================================================================
% devices description   
%====================================================================================
context(ctxfan, "localhost",  "TCP", "8001").
context(ctxweightsensor, "localhost",  "TCP", "8002").
context(ctxthermometer, "localhost",  "TCP", "8003").
context(ctxoutsonar, "localhost",  "TCP", "8004").
context(ctxcarparking, "localhost",  "TCP", "8100").
 qactor( fanactor, ctxfan, "it.unibo.fanactor.Fanactor").
  qactor( weightsensoractor, ctxweightsensor, "it.unibo.weightsensoractor.Weightsensoractor").

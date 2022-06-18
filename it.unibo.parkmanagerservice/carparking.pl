%====================================================================================
% carparking description   
%====================================================================================
context(ctxcarparking, "localhost",  "TCP", "8000").
context(ctxfan, "localhost",  "TCP", "8001").
context(ctxweightsensor, "localhost",  "TCP", "8002").
context(ctxthermometer, "localhost",  "TCP", "8003").
context(ctxoutsonarsensor, "localhost",  "TCP", "8004").
context(ctxbasicrobot, "localhost",  "TCP", "8020").
 qactor( fanactor, ctxfan, "external").
  qactor( weightsensoractor, ctxweightsensor, "external").
  qactor( thermometeractor, ctxthermometer, "external").
  qactor( outsonaractor, ctxoutsonarsensor, "external").
  qactor( basicrobot, ctxbasicrobot, "external").

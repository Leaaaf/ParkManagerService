%====================================================================================
% thermometer description   
%====================================================================================
context(ctxthermometer, "localhost",  "TCP", "8003").
 qactor( thermometeractor, ctxthermometer, "it.unibo.thermometeractor.Thermometeractor").

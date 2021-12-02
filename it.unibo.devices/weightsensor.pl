%====================================================================================
% weightsensor description   
%====================================================================================
context(ctxweightsensor, "localhost",  "TCP", "8002").
 qactor( weightsensoractor, ctxweightsensor, "it.unibo.weightsensoractor.Weightsensoractor").

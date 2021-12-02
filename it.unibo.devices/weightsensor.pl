%====================================================================================
% weightsensor description   
%====================================================================================
context(ctxoutsonarsensor, "localhost",  "TCP", "8004").
 qactor( outsonaractor, ctxoutsonarsensor, "it.unibo.outsonaractor.Outsonaractor").

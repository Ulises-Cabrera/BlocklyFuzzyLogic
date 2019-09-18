var blocklyArea = document.getElementById('blocklyArea');
var blocklyDiv = document.getElementById('blocklyDiv');

var workspace = Blockly.inject(blocklyDiv,
    {
        toolbox: document.getElementById('toolbox'),
        zoom: {
                controls: false,
                wheel: true,
                startScale: 1.0
        },
        horizontalLayout: false
    }
);
var onresize = function(e) {
    // Compute the absolute coordinates and dimensions of blocklyArea.
    blocklyArea.style.height = window.innerHeight+"px";
    var element = blocklyArea;
    var x = 0;
    var y = 0;
    do {
      x += element.offsetLeft;
      y += element.offsetTop;
      element = element.offsetParent;
    } while (element);
    // Position blocklyDiv over blocklyArea.
    blocklyDiv.style.left = x + 'px';
    blocklyDiv.style.top = y + 'px';
    blocklyDiv.style.width = blocklyArea.offsetWidth + 'px';
    blocklyDiv.style.height = blocklyArea.offsetHeight + 'px';
    Blockly.svgResize(workspace);
 };
window.addEventListener('load', onresize);
//onresize();
//Blockly.svgResize(workspace);
Blockly.JavaScript.addReservedWords('code');
Blockly.JavaScript.STATEMENT_PREFIX = 'highlightBlock(%1);\n';
Blockly.JavaScript.addReservedWords('highlightBlock');

function highlightBlock(id) {
  workspace.highlightBlock(id);
}

var highlightPause = false;

function initApi(interpreter, scope) {
  // Add an API function for highlighting blocks.
  var wrapper = function(id) {
    highlightPause = true;
    return workspace.highlightBlock(id);
  };

  interpreter.setProperty(scope, 'highlightBlock',
      interpreter.createNativeFunction(wrapper));

  wrapper = function(){
     tutor.caminarDerecha()
     while(!tutor.recibirComandos());
  };

  interpreter.setProperty(scope, 'caminarDerecha',
    interpreter.createNativeFunction(wrapper));

   wrapper = function(){
        tutor.caminarIzquierda();
        while(!tutor.recibirComandos());
   };

  interpreter.setProperty(scope, 'caminarIzquierda',
     interpreter.createNativeFunction(wrapper));

  wrapper = function(){
    tutor.saltar();
    while(!tutor.recibirComandos());
  };

  interpreter.setProperty(scope, 'saltar',
    interpreter.createNativeFunction(wrapper));

  wrapper = function(){
     tutor.disparar();
     while(!tutor.recibirComandos());
  };

  interpreter.setProperty(scope, 'disparar',
    interpreter.createNativeFunction(wrapper));

  wrapper = function(){
    return tutor.enemigoDeFrente();
  }

  interpreter.setProperty(scope, 'enemigoDeFrente', interpreter.createNativeFunction(wrapper));

  interpreter.setProperty(scope, 'juegoTerminado', interpreter.createNativeFunction(function(){ return tutor.juegoTerminado() }));
}

var myInterpreter;
var lastId;
var cerrarWebView = false;

function doStep(){
    do {
        try {
          var hasMoreCode = myInterpreter.step();
          if (tutor.respawn()){
            break;
          }else if (tutor.reachGoal()){
            clearTimeout(lastId);
            break;
          }
          if (hasMoreCode && highlightPause && !cerrarWebView){
            lastId = window.setTimeout(doStep, 10);
          } else if (!hasMoreCode){
             tutor.finalizarNivel();
          }
        } finally {
        }
     } while (hasMoreCode && !highlightPause && !cerrarWebView);
}

function getCodeBlockly(){
    tutor.codigoBlocklyGenerado(Blockly.JavaScript.workspaceToCode(workspace));
}

function runBlockly(){
    var parent = this;
    tutor.prepararNivel();
    code = Blockly.JavaScript.workspaceToCode(workspace);
    myInterpreter = new Interpreter(code, initApi);

    doStep();
}

function detenerEjecucion(){
    cerrarWebView = true;
    if (lastId){
        clearTimeout(lastId);
        tutor.timerStoped();
    }
}
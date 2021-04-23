package camfaker

import org.python.core.PyInstance
import org.python.util.PythonInterpreter

class InterpreterExample {
  var interpreter: PythonInterpreter? = null
  fun execfile(fileName: String?) {
    interpreter!!.execfile(fileName)
  }

  fun createClass(className: String, opts: String): PyInstance {
    return interpreter!!.eval("$className($opts)") as PyInstance
  }

  companion object {
    @JvmStatic
    fun main(gargs: Array<String>) {
      val ie = InterpreterExample()
      ie.execfile("hello.py")
      val hello = ie.createClass("Hello", "None")
      hello.invoke("run")
    }
  }

  init {
    PythonInterpreter.initialize(
      System.getProperties(),
      System.getProperties(), arrayOfNulls(0)
    )
    interpreter = PythonInterpreter()
  }
}

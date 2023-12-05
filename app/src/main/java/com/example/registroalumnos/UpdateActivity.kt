package com.example.registroalumnos

import android.os.Bundle
import android.widget.Toast
import com.example.registroalumnos.database.Alumnos
import com.example.registroalumnos.database.AlumnosApp
import com.example.registroalumnos.databinding.ActivityMainBinding
import com.example.registroalumnos.databinding.ActivityUpdateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateActivity : ActivityWithMenus() {

    private lateinit var listaAlumnos: MutableList<Alumnos>

    // Declaramos el binding
    private lateinit var binding : ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Evento click del botón actualizar alumno
        binding.bActualizar.setOnClickListener(){

            var nombreAlumno = binding.NombreActualizar.text.toString()
            var cursoAlumno = binding.CursoActualizar.text.toString()

            // Validaciones
            if (nombreAlumno.isEmpty())
            {
                Toast.makeText(this, "No puede haber campos vacíos", Toast.LENGTH_SHORT).show()
            }
            else
            {

                actualizarAlumno(nombreAlumno, cursoAlumno)
                Toast.makeText(this, "Alumno actualizado", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun actualizarAlumno(alumno: String, curso:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val alumno = AlumnosApp.database.interfazDao().obteneralumnopornombre(alumno)

            if (listaAlumnos.isNotEmpty()) {
                val alumno = listaAlumnos[0]

                // Actualizar el curso del alumno
                alumno.curso = curso

                // Actualizar el alumno en la base de datos
                AlumnosApp.database.interfazDao().updateAlumnos(alumno)
            }
        }
    }


}
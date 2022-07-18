package com.rrat.mvvmtodoappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rrat.mvvmtodoappcompose.ui.addedittodo.AddEditTodoScreen
import com.rrat.mvvmtodoappcompose.ui.theme.MVVMTodoAppComposeTheme
import com.rrat.mvvmtodoappcompose.ui.todolist.TodoListScreen
import com.rrat.mvvmtodoappcompose.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMTodoAppComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.TODO_LIST)
                {
                    composable(Routes.TODO_LIST){
                        TodoListScreen(onNavigate = {navController.navigate(it.route)})
                    }
                    composable(Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                    arguments = listOf(
                            navArgument(name = "todoId")
                            {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ){
                        AddEditTodoScreen(onPopBackStack ={
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}

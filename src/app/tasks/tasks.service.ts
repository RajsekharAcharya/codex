import { Injectable } from "@angular/core";
import { newTaskData } from "./task/task.model";

@Injectable({
  providedIn: 'root'
})
export class TasksService {

 private tasks = [
    {
      id: 't1',
      userId: 'u1',
      title: 'Master Angular',
      summary:
        'Learn all the basic and advanced features of Angular & how to apply them.',
      dueDate: '2025-12-31',
    },
    {
      id: 't2',
      userId: 'u3',
      title: 'Build first prototype',
      summary: 'Build a first prototype of the online shop website',
      dueDate: '2024-05-31',
    },
    {
      id: 't3',
      userId: 'u3',
      title: 'Prepare issue template',
      summary:
        'Prepare and describe an issue template which will help with project management',
      dueDate: '2024-06-15',
    },
  ];


  getUserTasks(userId:string){
    return this.tasks.filter((task)=>task.userId === userId);
  }

  addTask(taskData: newTaskData,userId:string){
    this.tasks.push({
      id: Math.random().toString(),
      userId: userId,
      title:taskData.title,
      summary:taskData.summary,
      dueDate:taskData.date
    });
  }

  removeTask(taskId:string){
    this.tasks = this.tasks.filter((task)=>task.id !== taskId);
  }

}

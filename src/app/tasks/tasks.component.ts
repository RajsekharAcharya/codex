import { TasksService } from './tasks.service';
import { Component, Input } from '@angular/core';
import { TaskComponent } from './task/task.component';
import { NewTaskComponent } from './new-task/new-task.component';
import { newTaskData } from './task/task.model';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [TaskComponent,NewTaskComponent],
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css',
})
export class TasksComponent {
  @Input({ required: true }) userId!: string;
  @Input({ required: true }) name!: string;
  // @Input() name: string | undefined;
  isAddingTask = false;
  // private tasksService : TasksService;

  constructor(private tasksService: TasksService){
    // this.tasksService = tasksService;
  }
  get selectedUserTasks() {
    return this.tasksService.getUserTasks(this.userId);
  }

  onCompeteTask(id: string) {
   this.tasksService.removeTask(id);
  }

  onStartAddTask() {
    this.isAddingTask = true
    }

  onCloseAddTask(){
    this.isAddingTask = false
  }
}

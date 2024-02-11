import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DeleteDialogComponent } from './pages/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,DeleteDialogComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'newsApp';
}

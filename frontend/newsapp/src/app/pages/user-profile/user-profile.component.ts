import { Component, OnInit } from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import { Router, RouterOutlet } from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { User } from '../../model/user';
import { FormsModule } from '@angular/forms';
import { NewsService } from '../../service/service.service';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';
import {MatDialogModule,MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [MatToolbarModule,RouterOutlet,MatIconModule,FormsModule,MatButtonModule,MatDialogModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit{
  user: User = new User;
  constructor(private route:Router,private service:NewsService,public dialog: MatDialog){

  }
  ngOnInit(): void {
      this.getUser();
  }

  async getUser() {
    try {
      const userData: any = await this.service.getUserByEmail();
      this.user = userData; 
    } catch (error) {
      console.error('Error fetching user data', error);
    }
  }

  openUpdateDialog(updatedData: any): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '250px',
      data: { updatedData: updatedData ,
          title: 'Update Confirmation',
          content: 'Are you sure you want to update this item?'
        }
    });

    dialogRef.afterClosed().subscribe((result: string) => {
      if (result === 'ok') {
        this.updateUser(updatedData);
      }
    });
  }

  openDeleteDialog(): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '250px',
      data: {
          title: 'Delete Confirmation',
          content: 'Are you sure you want to delete this item?'
        }
    });

    dialogRef.afterClosed().subscribe((result: string) => {
      if (result === 'ok') {
        this.deleteUser();
      }
    });
  }

  async updateUser(updatedData:User){
    try {
      const userData: any = await this.service.updateUser(updatedData);
      this.user = userData; 
    } catch (error) {
      console.error('Error in updating user', error);
    }
  }

  deleteUser(){
    if(this.service.deleteUser()){
    localStorage.removeItem('token')
    localStorage.removeItem('useremail')
      this.route.navigateByUrl('/login');
    }
  }

  goBack(){
    this.route.navigateByUrl('/dashboard');
  }
}

import { Component } from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import { Router, RouterOutlet } from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { NewsService } from '../../service/service.service';
import { Wishlist } from '../../model/wishlist';
import {MatDialogModule,MatDialog} from '@angular/material/dialog';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [MatToolbarModule,RouterOutlet,CommonModule,MatCardModule,MatIconModule,MatDialogModule],
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.css'
})
export class WishlistComponent {
  wishlist:Wishlist[]=[];
  
  constructor(private service:NewsService,private route:Router,public dialog: MatDialog){
  }

  openDialog(newsId: any): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '250px',
      data: { newsId: newsId ,
          title: 'Delete Confirmation',
          content: 'Are you sure you want to delete this item?'
        }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'ok') {
        this.deleteNews(newsId);
      }
    });
  }

  async deleteNews(Id:any){ 
    await this.service.deleteNews(Id);
    this.wishlist=this.wishlist.filter(el=>el.id!=Id);
  }

  ngOnInit(): void {
      this.getWishlist();
  }

  async getWishlist() {
    this.wishlist= await this.service.getWishlist();
}

  goBack(){
    this.route.navigateByUrl('/dashboard');
  }

}


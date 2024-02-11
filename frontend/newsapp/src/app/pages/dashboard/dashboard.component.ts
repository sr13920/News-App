import { Component, Input, OnInit,OnChanges, SimpleChanges } from '@angular/core';
import { News } from '../../model/news';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';

import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { Router, RouterOutlet } from '@angular/router';
import { MatMenuModule } from '@angular/material/menu';
import { MatListModule } from '@angular/material/list';
import { FormsModule } from '@angular/forms';
import { NewsService } from '../../service/service.service';
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule,MatCardModule,MatIconModule,MatFormFieldModule,FormsModule,MatToolbarModule,MatButtonModule,MatListModule,MatMenuModule],
  providers:[NewsService],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{
  temp:String='';
  searchText:String='';
allNews:News[]=[];

  constructor(public service: NewsService,public route:Router) {}

  ngOnInit(): void {
      if(!localStorage.getItem('useremail')){
        this.route.navigateByUrl('/login')
      }
  }

  
getNews(){
var news = this.service.getNews()
if (this.searchText.trim() !== '') {
  return news.filter(el =>
     el.Title.toLowerCase().includes(this.searchText.toLowerCase())
   )
 }else{
  return news;
 }
}


profile(){
  this.route.navigateByUrl('/profile');
}

wishlist(){
  this.route.navigateByUrl('/wishlist');
}

logout(){
localStorage.removeItem('token')
  localStorage.removeItem('useremail')
    this.route.navigateByUrl('/login');
}

  bookMark(news:News){
    this.service.addToWishList(news).subscribe((data)=>console.log(data));
  }
  
  
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { News } from '../model/news';
import { Wishlist } from '../model/wishlist';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  public newsObj: News[] = [];
  useremail:String='';
  currentUser:String='';

  constructor(private http: HttpClient) {
    this.loadNews(); 
  }


  getNews(): News[] {
    return this.newsObj;
  }

  async updateUser(userObj:User){
    var content= await this.http.put('http://localhost:8087/user/updateUser/'+localStorage.getItem('useremail'),userObj).toPromise();
    return content;
  }

  deleteUser(){
    return this.http.delete('http://localhost:8087/user/deleteUser/'+localStorage.getItem('useremail')).subscribe((data:any)=>{
    
    })
  }

  async getUserByEmail(){
    var content:any =await this.http.get('http://localhost:8087/user/get/'+localStorage.getItem('useremail')).toPromise()
    return content;
  }

  deleteNews(id:Number){
    this.http.delete('http://localhost:8087/wishlist/removeNews/'+localStorage.getItem('useremail')+'/'+id).subscribe((data:any)=>{
      
    })
  }

  async getWishlist(){
    var content:any =await this.http.get('http://localhost:8087/wishlist/getNews/'+localStorage.getItem('useremail')).toPromise()
    return content.news;
  }

  getUseremail(token:String){
     this.http.get('http://localhost:8087/auth/getUserByToken',{
      'headers':{
        'Authorization':'Bearer '+token
      }
    }).subscribe((data:any)=>{
      this.useremail=data.email;
      localStorage.setItem('useremail',data.email);
      this.currentUser=data.email;
    })
  }

    getCurrentUser(){
    return this.currentUser;
  }

  addToWishList(newsObj:News){
     return this.http.post('http://localhost:8087/wishlist/addNews',{'useremail':localStorage.getItem('useremail'),news:[newsObj]})
  }

  fetchNews(): Observable<News[]> {
     return this.http.get<News[]>('http://localhost:8087/news/country-news?fromCountry=in&language=en');
  
  }

 

  loadNews(): void {
    this.fetchNews().subscribe((data:any) => {
      this.newsObj = data.news;
    });
  //   this.newsObj=[
  //     {
  //         "Title": "Codex committee finalises quality standards for 5 spices",
  //         "Description": "The 7th session of the Codex Committee on Spices and Culinary Herbs (CCSCH) held at Kochi has finalised the quality standards for 5 spices, namely small cardamom, turmeric, juniper berry, allpice and star anise, the Commerce Ministry said on Saturday.",
  //         "Image": "https://www.punjabnewsexpress.com/images/article/article238773.jpg"
  //     },
  //     {
  //         "Title": "A month of marital bliss: Ira shares unseen wedding photo with Nupur",
  //         "Description": "Bollywood’s ‘perfectionist’ Aamir Khan’s daughter Ira Khan on Saturday celebrated one month of martial bliss with Nupur Shikhare, by sharing an unseen picture from her wedding diary.",
  //         "Image": "https://www.punjabnewsexpress.com/images/article/article238779.jpg"
  //     },
  //     {
  //         "Title": "Secured creditor can forfeit earnest money if there is a default in payment by auction-purchaser: SC",
  //         "Description": "In a latest ruling, the Supreme Court has held that the Bank being a secured creditor can forfeit the earnest money of a successful auction-purchaser if the latter fails in depositing the balance amount within the timeline specified under the SARFAESI Rules.",
  //         "Image": "https://www.punjabnewsexpress.com/images/article/article238771.jpg"
  //     },
  //     {
  //         "Title": "People in Karnataka have to live amicably: Shivakumar on seer controversy",
  //         "Description": "Karnataka Deputy Chief Minister D.K. Shivakumar on Saturday said that people in the state have to live amicably and not stoke religious tension.",
  //         "Image": "https://www.punjabnewsexpress.com/images/article/article238770.jpg"
  //     },
  //     {
  //         "Title": "SBI records 35% fall in Q3 net profit at Rs 9,163 crore",
  //         "Description": "State Bank of India (SBI) on Saturday reported a 35 per cent decline in net profit at Rs 9,163 crore for the October-December quarter of the current financial year.",
  //         "Image": "https://www.punjabnewsexpress.com/images/article/article238774.jpg"
  //     },
  //     {
  //         "Title": "As Poonam Pandey pops up from 'dead', questions swirl on ethics of stunt",
  //         "Description": "Controversial actress-model Poonam Pandey has yet again made headlines after she said she \"faked\" her death to start a national conversation on cervical cancer. In the bargain, like she always manages to do, she has left behind a trail of polarised views on social media.",
  //         "Image": "https://www.punjabnewsexpress.com/images/article/article238780.jpg"
  //     },
  //     {
  //         "Title": "Google officially ends its 'cached' web page feature",
  //         "Description": "Google has officially retired its 'cached' web page feature as the company said that it was 'no longer required'.",
  //         "Image": "https://www.punjabnewsexpress.com/images/article/article238783.jpg"
  //     },
  //     {
  //         "Title": "Kangana Ranaut: Was financially supporting my family, ruffling many feathers at 21",
  //         "Description": "Bollywood actress Kangana Ranaut has joined the viral social media trend of “lets see you at 21”, in which she said that she was supporting her family, won a National Award and ruffled many feathers.",
  //         "Image": "https://www.punjabnewsexpress.com/images/article/article238782.jpg"
  //     },
  //     {
  //         "Title": "Nikhat and Lovlina to spearhead India’s challenge in 75th Strandja Memorial Tournament",
  //         "Description": "Reigning world champions Nikhat Zareen and Lovlina Borgohain are set to lead Indian challenge as the Boxing Federation of India named 19-member squad for the 75th Strandja Memorial Tournament to be held in Sofia, Bulgaria from February 3 to February 11.",
  //         "Image": "https://www.punjabnewsexpress.com/images/article/article238785.jpg"
  //     },
  //     {
  //         "Title": "Revanth Reddy most uncivilised CM in country, says Harish Rao",
  //         "Description": "Addressing a gathering at Mahabubabad parliamentary constituency preparatory meeting at Bhadrachalam on Saturday, Harish Rao appreciated the cadre for working hard to win the Bhadrachalam Assembly seat and urged them to make efforts to win the Mahabubabad Lok Sabha seat for the third time.",
  //         "Image": "https://cdn.telanganatoday.com/wp-content/uploads/2024/02/Harish-Rao.png"
  //     }
  // ]

  }
}

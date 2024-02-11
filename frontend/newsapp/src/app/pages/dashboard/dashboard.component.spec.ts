import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardComponent } from './dashboard.component';
import { NewsService } from '../../service/service.service';
import { RouterTestingModule } from '@angular/router/testing';
import { FormsModule } from '@angular/forms';
import { News } from '../../model/news'; 
import { HttpClientModule } from '@angular/common/http';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let newsService: NewsService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    //  declarations: [DashboardComponent], 
      imports: [RouterTestingModule, FormsModule,HttpClientModule],
      providers: [NewsService]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    newsService = TestBed.inject(NewsService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should redirect to login if useremail is not in localStorage', () => {
    spyOn(localStorage, 'getItem').and.returnValue(null);
    const navigateSpy = spyOn(component.route, 'navigateByUrl');
    component.ngOnInit();
    expect(navigateSpy).toHaveBeenCalledWith('/login');
  });

  it('should not redirect to login if useremail is in localStorage', () => {
    spyOn(localStorage, 'getItem').and.returnValue('test@example.com');
    const navigateSpy = spyOn(component.route, 'navigateByUrl');
    component.ngOnInit();
    expect(navigateSpy).not.toHaveBeenCalled();
  });

  it('should call service method to get news', () => {
    const news: News[] = [{ Title: 'Test News 1', Description: 'Description 1', Image: 'Image URL 1' }, { Title: 'Test News 2', Description: 'Description 2', Image: 'Image URL 2' }];
    spyOn(component.service, 'getNews').and.returnValue(news);
    component.searchText = '';
    expect(component.getNews()).toEqual(news);
  });

//   it('should filter news based on searchText', () => {
//     const news: News[] = [{ Title: 'Test News 1', Description: 'Description 1', Image: 'Image URL 1' }, { Title: 'Test News 2', Description: 'Description 2', Image: 'Image URL 2' }];
//     spyOn(component.service, 'getNews').and.returnValue(news);
//     component.searchText = 'test 1';
//     expect(component.getNews()).toEqual([{ Title: 'Test News 1', Description: 'Description 1', Image: 'Image URL 1' }]);
//   });

//   it('should call service method to add news to wishlist', () => {
//     const news: News = { Title: 'Test News 1', Description: 'Description 1', Image: 'Image URL 1' };
//     spyOn(component.service, 'addToWishList').and.stub();
//     component.bookMark(news);
//     expect(component.service.addToWishList).toHaveBeenCalledWith(news);
//   });
});

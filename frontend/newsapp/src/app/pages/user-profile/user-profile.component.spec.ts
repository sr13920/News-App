import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { RouterTestingModule } from '@angular/router/testing';
import { UserProfileComponent } from './user-profile.component';
import { NewsService } from '../../service/service.service';
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { of } from 'rxjs';

describe('UserProfileComponent', () => {
  let component: UserProfileComponent;
  let fixture: ComponentFixture<UserProfileComponent>;
  let mockDialog: jasmine.SpyObj<MatDialog>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockNewsService: jasmine.SpyObj<NewsService>;

  beforeEach(async () => {
    mockDialog = jasmine.createSpyObj('MatDialog', ['open']);
    mockRouter = jasmine.createSpyObj('Router', ['navigateByUrl']);
    mockNewsService = jasmine.createSpyObj('NewsService', ['getUserByEmail', 'updateUser', 'deleteUser']);

    await TestBed.configureTestingModule({
     // declarations: [UserProfileComponent],
      imports: [MatDialogModule, RouterTestingModule, FormsModule],
      providers: [
        { provide: MatDialog, useValue: mockDialog },
        { provide: Router, useValue: mockRouter },
        { provide: NewsService, useValue: mockNewsService }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('ngOnInit should call getUser', () => {
    spyOn(component, 'getUser');
    component.ngOnInit();
    expect(component.getUser).toHaveBeenCalled();
  });

  it('openUpdateDialog should open a dialog', () => {
    const dialogRefSpyObj = jasmine.createSpyObj({ afterClosed: of('ok') });
    mockDialog.open.and.returnValue(dialogRefSpyObj);
    component.openUpdateDialog({});
    expect(mockDialog.open).toHaveBeenCalled();
  });
  

  it('openDeleteDialog should open a dialog', () => {
    const dialogRefSpyObj = jasmine.createSpyObj({ afterClosed: of('ok') });
    component.openDeleteDialog();
    expect(mockDialog.open).toHaveBeenCalled();
  });

  // it('deleteUser should remove token and useremail from localStorage and navigate to login', () => {
  //   mockNewsService.deleteUser.and.returnValue(true);
  //   component.deleteUser();
  //   expect(localStorage.removeItem).toHaveBeenCalledWith('token');
  //   expect(localStorage.removeItem).toHaveBeenCalledWith('useremail');
  //   expect(mockRouter.navigateByUrl).toHaveBeenCalledWith('/login');
  // });
});

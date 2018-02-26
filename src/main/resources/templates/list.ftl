<div><table class="table table-hover"><tr><td>
<div class="panel panel-default">
        <!-- Default panel contents -->

        <div class="panel-heading"><span class="lead">User </span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submitUser()" name="myForm" class="form-horizontal">
	               <#-- <input type="hidden" ng-model="ctrl.user.id" />-->
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="uname">Name</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.user.name" id="uname" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="3"/>
	                        </div>
	                    </div>
	                </div>

	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="password">Password</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.user.password" id="password" class="form-control input-sm" placeholder="Enter your Password." <#--required ng-pattern="ctrl.onlyIntegers"-->/>
	                        </div>
	                    </div>
	                </div>
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="role">Role</label>
                            <div class="col-md-7">
                                <select name="mySelect" id="mySelect"
                                        ng-options="option.name for option in ctrl.getAllRoles() track by option.dictionary_id"
                                        ng-model="ctrl.user.role"></select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="enabled">Active</label>
                            <div class="col-md-7">
                                <input type="checkbox" ng-model="ctrl.user.enabled"
                                       ng-true-value="'1'" ng-false-value="'0'">
                            </div>
                        </div>
                    </div>



	                <div class="row">
	                    <div class="form-actions floatRight">
	                        <input type="submit"  value="{{!ctrl.user.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>
    </div>
</td><td>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Users </span></div>
		<div class="panel-body">
			<div class="table-responsive">
		        <table class="table table-hover">
		            <thead>
		            <tr>
		                <th>ID</th>
		                <th>NAME</th>
						<th>PASSWORD</th>
                        <th>ROLE</th>
		                <th width="100"></th>
		                <th width="100"></th>
		            </tr>
		            </thead>
		            <tbody>
		            <tr ng-repeat="u in ctrl.getAllUsers()">
		                <td>{{u.id}}</td>
		                <td>{{u.name}}</td>
		                <td>{{u.password}}</td>
		                <td>{{u.role.name}}</td>
		                <td><button type="button" ng-click="ctrl.editUser(u.id)" class="btn btn-success custom-width">Edit</button></td>
		                <td><button type="button" ng-click="ctrl.removeUser(u.id)" class="btn btn-danger custom-width">Remove</button></td>
		            </tr>
		            </tbody>
		        </table>
			</div>
		</div>
    </div>
</td></tr></table></div>
<div><table class="table table-hover"><tr><td>
	<div class="panel panel-default">
        <!-- Default panel contents -->

        <div class="panel-heading"><span class="lead">Role </span></div>
        <div class="panel-body">
            <div class="formcontainer">
                <div class="alert alert-success" role="alert" ng-if="ctrl.successRoleMessage">{{ctrl.successRoleMessage}}</div>
                <div class="alert alert-danger" role="alert" ng-if="ctrl.errorRoleMessage">{{ctrl.errorRoleMessage}}</div>
                <form ng-submit="ctrl.submitRole()" name="myRoleForm" class="form-horizontal">
                    <#--<input type="hidden" ng-model="ctrl.role.roleId" />-->
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="rname">Role</label>
                            <div class="col-md-7">
                                <input type="text" ng-model="ctrl.role.name" id="rname" class="username form-control input-sm" placeholder="Enter new role" required ng-minlength="3"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-actions floatRight">
                            <input type="submit"  value="{{!ctrl.role.roleId ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myRoleForm.$invalid || myRoleForm.$pristine">
                            <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myRoleForm.$pristine">Reset Form</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</td><td>
	<div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Roles </span></div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ROLE_ID</th>
                        <th>ROLE</th>
                        <th width="100"></th>
                        <th width="100"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="r in ctrl.getAllRoles()">
                        <td>{{r.dictionary_id}}</td>
                        <td>{{r.name}}</td>
                        <td><button type="button" ng-click="ctrl.editRole(r.roleId)" class="btn btn-success custom-width">Edit</button></td>
                        <td><button type="button" ng-click="ctrl.removeRole(r.roleId)" class="btn btn-danger custom-width">Remove</button></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
	</div>
</td></tr></table></div>
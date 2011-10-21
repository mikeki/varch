require 'test_helper'

class SourceCodesControllerTest < ActionController::TestCase
  setup do
    @source_code = source_codes(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:source_codes)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create source_code" do
    assert_difference('SourceCode.count') do
      post :create, :source_code => @source_code.attributes
    end

    assert_redirected_to source_code_path(assigns(:source_code))
  end

  test "should show source_code" do
    get :show, :id => @source_code.to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => @source_code.to_param
    assert_response :success
  end

  test "should update source_code" do
    put :update, :id => @source_code.to_param, :source_code => @source_code.attributes
    assert_redirected_to source_code_path(assigns(:source_code))
  end

  test "should destroy source_code" do
    assert_difference('SourceCode.count', -1) do
      delete :destroy, :id => @source_code.to_param
    end

    assert_redirected_to source_codes_path
  end
end

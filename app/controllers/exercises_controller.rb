class ExercisesController < ApplicationController
  before_filter :find_course
  
  def find_course
    @course = Course.find(params[:course_id])
  end
  
  # GET /exercises
  # GET /exercises.xml
  def index
    @exercises = @course.exercises.order("similarity_avarage desc")

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @exercises }
    end
  end

  # GET /exercises/1
  # GET /exercises/1.xml
  def show
    @exercise = @course.exercises.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @exercise }
    end
  end

  # GET /exercises/new
  # GET /exercises/new.xml
  def new
    @exercise = Exercise.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @exercise }
    end
  end

  # GET /exercises/1/edit
  def edit
    @exercise = @course.exercises.find(params[:id])
  end

  # POST /exercises
  # POST /exercises.xml
  def create
    @exercise = @course.exercises.build(params[:exercise])
    current_user.exercises << @exercise

    respond_to do |format|
      if @exercise.save
        format.html { redirect_to(course_exercise_path(@course, @exercise), :notice => 'Exercise was successfully created.') }
        format.xml  { render :xml => @exercise, :status => :created, :location => @exercise }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @exercise.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /exercises/1
  # PUT /exercises/1.xml
  def update
    @exercise = @course.exercises.find(params[:id])

    respond_to do |format|
      if @exercise.update_attributes(params[:exercise])
        format.html { redirect_to(course_exercise_path(@course, @exercise), :notice => 'Exercise was successfully updated.') }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @exercise.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /exercises/1
  # DELETE /exercises/1.xml
  def destroy
    @exercise = @course.exercises.find(params[:id])
    @exercise.destroy

    respond_to do |format|
      format.html { redirect_to(course_exercises_path(@course)) }
      format.xml  { head :ok }
    end
  end
  
  def comparison
    @exercise = @course.exercises.find(params[:id])
    @similarities = @exercise.similarities
  end
end

require 'zip'
require 'find'
require 'uri'
require 'net/http'

class SourceCodesController < ApplicationController
  before_filter :find_exercise
  
  def find_exercise
    @exercise= Exercise.find(params[:exercise_id])
  end
  
  
  # GET /source_codes
  # GET /source_codes.xml
  def index
    @source_codes = @exercise.source_codes
    @input = SimilarityInput.new

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @source_codes }
    end
  end

  # GET /source_codes/1
  # GET /source_codes/1.xml
  def show
    @source_code = @exercise.source_codes.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @source_code }
    end
  end

  # GET /source_codes/new
  # GET /source_codes/new.xml
  def new
    @source_code = SourceCode.new
    @zipfile = ZipFile.new
    
    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @source_code }
    end
  end

  # GET /source_codes/1/edit
  def edit
    @source_code = @exercise.source_codes.find(params[:id])
  end

  # POST /source_codes
  # POST /source_codes.xml
  def create
    @source_code = @exercise.source_codes.build(params[:source_code])

    respond_to do |format|
      if @source_code.save
        format.html { redirect_to(course_exercise_source_code_path(@exercise.course, @exercise, @source_code), :notice => 'Source code was successfully created.') }
        format.xml  { render :xml => @source_code, :status => :created, :location => @source_code }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @source_code.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /source_codes/1
  # PUT /source_codes/1.xml
  def update
    @source_code = @exercise.source_codes.find(params[:id])

    respond_to do |format|
      if @source_code.update_attributes(params[:source_code])
        format.html { redirect_to(course_exercise_source_code_path(@exercise.course, @exercise, @source_code), :notice => 'Source code was successfully updated.') }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @source_code.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /source_codes/1
  # DELETE /source_codes/1.xml
  def destroy
    @source_code = @exercise.source_codes.find(params[:id])
    @source_code.destroy

    respond_to do |format|
      format.html { redirect_to(course_exercise_source_codes_url(@exercise.course, @exercise)) }
      format.xml  { head :ok }
    end
  end
  
  def upload_file
    tmp = params[:zip_file][:file].tempfile
    file = "#{RAILS_ROOT}/public/zipfiles/#{params[:zip_file][:file].original_filename.tr(' ','-')}"
    FileUtils.cp(tmp.path, file)
    unzip(file, "#{RAILS_ROOT}/public/zipfiles")
    FileUtils.rm(file)
    redirect_to course_exercise_path(@exercise.course, @exercise)
  end
  
  def unzip(file, to)
    if !File.exists?( to )
      FileUtils.mkdir( to )
    end
    #open Zip File
    zip_file = Zip::ZipFile.open(file)
    #UnZip file
    zip_file.reverse_each do |entry|
      
      file_path = File.join( to, entry.to_s )
      if File.exists?( file_path ) && !File.directory?(file_path)
        FileUtils.rm( file_path )
      end
      zip_file.extract( entry, file_path )
    end
    copy_to_database(file)
  end
  
  def copy_to_database(file)
    file_path = file.chomp(".zip")
    Find.find(file_path) do |subfile|
      if subfile != file_path
        if !File.directory?(subfile) && !File.extname(subfile).index('~')
          @source_code = @exercise.source_codes.build
          @source_code.language = File.extname(subfile)
          @source_code.student_id = File.basename(subfile, @source_code.language)
          file = File.open(subfile, "r")
          code = ""
          while(line = file.gets)
            code+=line
          end
          @source_code.code = code
          @source_code.save
        end
      end
    end
    FileUtils.rm_rf(file_path)
  end
   
  def compare
    # Prepare variables for the HASH
    #
    json = ActiveSupport::JSON
    algorithms = params[:algorithms]
    files = []
    params[:files].each do |index|
      source_code = SourceCode.find(index)
      files << {:id => index, :code => source_code.code}
    end
    
    # Create parameters HASH
    #
    parametros = {}
    parametros[:params] = json.encode({:algorithms => algorithms, :files => files})
    
    # Sends HTTP post to python webservice that runds the algorithms
    #
    response = Net::HTTP.post_form(URI.parse('http://localhost:3001/compare'), parametros)
    
    # Process the response and saves the similarities on database
    #
    
    @similarities = []
    case response
      when Net::HTTPSuccess, Net::HTTPRedirection
        json.decode(response.body).each do |data_hash|
          similarity1_id = data_hash["id"]
          data_hash["similarities"].each do |compared|
            similarity2_id = compared["id"]
            compared["similarity"].each do |key, percentage|
              similarity = Similarity.new
              similarity.source_code1_id = similarity1_id
              similarity.source_code2_id = similarity2_id
              similarity.used_algorithm = key
              similarity.similarity = percentage
              #similarity.save
              
              @similarities << similarity
            end
          end
        end
        Similarity.import @similarities, :validate => false
      else
        response.error!
    end
  end
   
end

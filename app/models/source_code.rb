require 'zip'
require 'find'

class SourceCode < ActiveRecord::Base
  belongs_to :exercise, :class_name => "Exercise", :foreign_key => :exercise_id
 
  has_many :similarity1, :class_name => "Similarity", :foreign_key => :source_code1_id, :dependent => :destroy
  has_many :similarity2, :class_name => "Similarity", :foreign_key => :source_code2_id, :dependent => :destroy
  
  
  def self.unzip(file, to, exercise)
    if !File.exists?( to )
      FileUtils.mkdir( to )
    end
    #open Zip File
    #UnZip file
    begin
      zip_file = Zip::ZipFile.open(file)
      zip_file.each do |entry|
        SourceCode.extract(entry, to, zip_file)
      end
    rescue
      zip_file = Zip::ZipFile.open(file)
      zip_file.reverse_each do |entry|
        SourceCode.extract(entry, to, zip_file)
      end
    end
    self.copy_to_database(file, exercise)
  end
  
  def self.extract(entry, to, zip_file)
    file_path = File.join( to, entry.to_s )
    if File.exists?( file_path ) && !File.directory?(file_path)
      FileUtils.rm( file_path )
    end
    zip_file.extract( entry, file_path )
  end
  
  def self.copy_to_database(file, exercise)
    file_path = file.chomp(".zip")
    begin
      Find.find(file_path) do |subfile|
        if subfile != file_path
          if !File.directory?(subfile) && !File.extname(subfile).index('~')
            student_id = File.dirname(subfile).split("/").last
            @source_code = exercise.source_codes.find_by_student_id(student_id) || exercise.source_codes.build
            @source_code.language = File.extname(subfile)
            @source_code.student_id = student_id
            file = File.open(subfile, "r")
            code = ""
            while(line = file.gets)
              code+=line.encode("UTF-8")
            end
            @source_code.code = code.encode("UTF-8")
            @source_code.save
          end
        end
      end
      FileUtils.rm_rf(file_path)
    rescue
      puts "error en #{file_path}"
    end
  end
  
end

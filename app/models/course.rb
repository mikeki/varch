class Course < ActiveRecord::Base
  
  has_many :exercises
  validates_presence_of :name
  validates_presence_of :courseid
  
end

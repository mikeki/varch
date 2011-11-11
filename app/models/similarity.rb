require 'uri'
require 'net/http'

class Similarity < ActiveRecord::Base
  
  belongs_to :source_code1, :class_name=> "SourceCode", :foreign_key => :source_code1_id
  belongs_to :source_code2, :class_name=> "SourceCode", :foreign_key => :source_code2_id
  has_many :algorithms
  belongs_to :exercise
  
  
  def self.create_from_response(response)
    similarities = []
    json = ActiveSupport::JSON
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
              similarity.algorithms.build(:type=>key.to_i, :percentage=>percentage)
              #similarity.similarity = percentage
              #similarity.save
              
              similarities << similarity
            end
          end
        end
        Similarity.import similarities, :validate => false
        similarities.each do |similarity|
          Algorithm.import similarity.algorithms
        end
      else
        response.error!
    end
    similarities
  end
  
end

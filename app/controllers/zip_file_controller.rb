class ZipFileController < ApplicationController
  def upload
    file = params[:zip_file][:file]
    FileUtils.copy(file.local_path, "#{RAILS_ROOT}/public/zipfiles/#{file.original_filename}")
    redirect_to 
    
  end

end

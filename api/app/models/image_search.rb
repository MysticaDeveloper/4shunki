class ImageSearch
  require 'cgi'
  require "base64"
  require "net/https"

  def self.search(search_word_org)
    api_url = "https://api.datamarket.azure.com/Bing/Search/v1/Image"
    api_key = "fmMTpaDLcU5qmdPpGUjymWuKDdXGbUcypMq+I9wK3Hk"

    search_word = CGI.escape("'#{search_word_org}'")
    adult_off = CGI.escape("'off'")
    adult_strict = CGI.escape("'Strict'")
    filter = CGI.escape("'Size:Small'")
    https = Net::HTTP.new("api.datamarket.azure.com", 443)
    https.use_ssl = true
#    https.ca_file = ca_file_path
    https.verify_mode  = OpenSSL::SSL::VERIFY_NONE
    https.verify_depth = 5
    # セッション開始
    urls = []
    https.start do |h|
      req1 = Net::HTTP::Get.new("/Bing/Search/v1/Image?Query=#{search_word}&$format=json&ImageFilters=#{filter}&Adult=#{adult_off}")
      req1.basic_auth( api_key, api_key )
      res1 = h.request( req1 )
      results = JSON.parse(res1.body)["d"]["results"]
      results.each do |i|
        urls << i["MediaUrl"]
      end

      req2 = Net::HTTP::Get.new("/Bing/Search/v1/Image?Query=#{search_word}&$format=json&$skip=100&ImageFilters=#{filter}&Adult=#{adult_off}")
      req2.basic_auth( api_key, api_key )
      res2 = h.request( req2 )
      results = JSON.parse(res2.body)["d"]["results"]
      results.each do |i|
        urls << i["MediaUrl"]
      end

      req3 = Net::HTTP::Get.new("/Bing/Search/v1/Image?Query=#{search_word}&$format=json&ImageFilters=#{filter}&Adult=#{adult_strict}")
      req3.basic_auth( api_key, api_key )
      res3 = h.request( req3 )
      results = JSON.parse(res3.body)["d"]["results"]
      results.each do |i|
        urls.delete(i["MediaUrl"])
      end

      req4 = Net::HTTP::Get.new("/Bing/Search/v1/Image?Query=#{search_word}&$format=json&ImageFilters=#{filter}&Adult=#{adult_strict}&$skip=100")
      req4.basic_auth( api_key, api_key )
      res4 = h.request( req4 )
      results = JSON.parse(res4.body)["d"]["results"]
      results.each do |i|
        urls.delete(i["MediaUrl"])
      end

      urls
    end

  end
end


#https://api.datamarket.azure.com/Bing/Search/v1/Image?Query=%27%E5%B7%A8%E4%B9%B3%27&Adult=%27Off%27&ImageFilters=%27Size%3ALarge%27
#https://api.datamarket.azure.com/Bing/Search/v1/Image?Query=%27%E5%B7%A8%E4%B9%B3%27&Adult=%27Strict%27&ImageFilters=%27Size%3ALarge%27
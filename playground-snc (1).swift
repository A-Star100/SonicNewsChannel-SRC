import UIKit
import WebKit

class MyViewController: UIViewController, WKNavigationDelegate {
  
  @IBOutlet weak var webView: WKWebView! // This should be connected to your storyboard element
  
  override func viewDidLoad() {
    super.viewDidLoad()
    
    webView.navigationDelegate = self // Set the delegate for handling navigation events (optional)
    guard let url = URL(string: "https://sites.google.com/view/snc-mobile/home")! // Replace with the actual website URL
    webView.load(URLRequest(url: url))
  }
  
  // Optional functions for handling navigation events (like loading progress or errors)
  func webView(_ webView: WKWebView, didStartProvisionalNavigation navigation: WKNavigation!) {
    print("Website started loading")
  }
  
  func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
    print("Website loading complete")
  }
  
  func webView(_ webView: WKWebView, didFail navigation: WKNavigation!, withError error: Error) {
    print("Error loading website: \(error)")
  }
}

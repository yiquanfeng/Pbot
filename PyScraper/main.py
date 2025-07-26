import undetected_chromedriver as uc
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time

if __name__ == '__main__':
    driver = uc.Chrome(use_subprocess=True)

    try:
        driver.get("https://www.phoronix.com/rss.php")
        time.sleep(5) # 等待页面稳定

        try:
            # WebDriverWait(等待工具) + EC (预期条件) 是最稳定可靠的方法
            # 等待 iframe 出现，最多等待 15 秒
            iframe = WebDriverWait(driver, 15).until(
                EC.presence_of_element_located((By.XPATH, '//iframe[starts-with(@title, "Cloudflare")]'))
            )
            print("找到 Cloudflare iframe，正在切换...")
            driver.switch_to.frame(iframe)

            # 2. 在 iframe 中寻找并点击复选框
            # 有时是一个 input，有时是一个 span，需要根据实际情况分析
            checkbox = WebDriverWait(driver, 15).until(
                EC.element_to_be_clickable((By.XPATH, '//input[@type="checkbox"] | //span[@class="mark"]'))
            )
            print("找到复选框，正在点击...")
            checkbox.click()
            
            # 3. 点击后，切回主文档
            driver.switch_to.default_content()
            print("点击完成，已切回主页面。")
            # 这里等待某个只有在主页面通过验证后才会出现的元素
            # WebDriverWait(driver, 20).until(
            #     EC.presence_of_element_located((By.XPATH, "YOUR_TARGET_ELEMENT_XPATH_AFTER_LOGIN"))
            # )
            print("Cloudflare 验证通过！")

        except Exception as e:
            print(f"处理 Cloudflare 点击验证时出错: {e}")
        
        page_source = driver.page_source
        with open('phoronix_rss.xml', 'w', encoding='utf-8') as f:
            f.write(page_source)
        print("RSS内容已保存为 'phoronix_rss.xml'")
    finally:
        driver.quit()

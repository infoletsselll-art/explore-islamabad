import re

with open("public/index.html", "r", encoding="utf-8") as f:
    html = f.read()

# 1. Replace the CSS block
dark_css = """<style>
    :root {
      --primary: #10B981;
      --primary-dark: #059669;
      --primary-light: #34D399;
      --primary-accent: #25D366;
      --accent-gold: #F59E0B;
      --accent-gold-light: #FEF3C7;
      --bg-dark: #080E0A;
      --bg-cream: #080E0A;
      --surface: #111C15;
      --surface-card: #15241C;
      --surface-card-hover: #1A2F24;
      --surface-elevated: #182A20;
      --surface-mint: rgba(16, 185, 129, 0.15);
      --text-main: #FFFFFF;
      --text-muted: #9CA3AF;
      --text-subtle: #6EE7B7;
      --border-subtle: #1E382A;
      --border-focus: #10B981;
      --shadow-sm: 0 4px 12px rgba(0,0,0,0.5);
      --shadow-md: 0 10px 24px rgba(0,0,0,0.65), 0 0 16px rgba(16, 185, 129, 0.12);
      --shadow-lg: 0 20px 45px rgba(0,0,0,0.8), 0 0 25px rgba(16, 185, 129, 0.18);
      --radius: 16px;
    }

    * {
      box-sizing: border-box;
      margin: 0;
      padding: 0;
      font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
    }

    body {
      background-color: var(--bg-dark);
      color: var(--text-main);
      line-height: 1.6;
      min-height: 100vh;
      display: flex;
      flex-direction: column;
    }

    /* Header & Navigation */
    header {
      background-color: rgba(17, 28, 21, 0.95);
      border-bottom: 1px solid var(--border-subtle);
      position: sticky;
      top: 0;
      z-index: 100;
      backdrop-filter: blur(12px);
    }

    .nav-container {
      max-width: 1100px;
      margin: 0 auto;
      padding: 14px 20px;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    .brand {
      display: flex;
      align-items: center;
      gap: 12px;
      text-decoration: none;
      color: var(--text-main);
    }

    .brand-logo {
      width: 44px;
      height: 44px;
      background: linear-gradient(135deg, #10B981, #059669);
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: #FFFFFF;
      font-weight: 800;
      font-size: 20px;
      box-shadow: 0 4px 12px rgba(16, 185, 129, 0.35);
    }

    .brand-text .brand-title,
    .brand-text h1 {
      font-size: 20px;
      font-weight: 800;
      letter-spacing: -0.5px;
      color: #FFFFFF;
    }

    .brand-text span {
      font-size: 12px;
      color: #6EE7B7;
      font-weight: 500;
      display: block;
    }

    .header-actions {
      display: flex;
      gap: 10px;
      align-items: center;
    }

    /* Buttons */
    .btn {
      display: inline-flex;
      align-items: center;
      justify-content: center;
      gap: 8px;
      padding: 10px 18px;
      border-radius: 12px;
      font-size: 14px;
      font-weight: 600;
      text-decoration: none;
      transition: all 0.2s ease-in-out;
      cursor: pointer;
      border: none;
    }

    .btn-primary {
      background: linear-gradient(135deg, #10B981, #059669);
      color: #FFFFFF;
      box-shadow: 0 4px 14px rgba(16, 185, 129, 0.35);
    }

    .btn-primary:hover {
      background: linear-gradient(135deg, #059669, #047857);
      transform: translateY(-2px);
      box-shadow: 0 6px 18px rgba(16, 185, 129, 0.5);
    }

    .btn-accent {
      background: linear-gradient(135deg, #25D366, #10B981);
      color: #FFFFFF;
    }

    .btn-accent:hover {
      transform: translateY(-2px);
      box-shadow: 0 6px 16px rgba(37, 211, 102, 0.4);
    }

    .btn-outline {
      border: 1.5px solid #10B981;
      background-color: var(--surface-card);
      color: #FFFFFF;
    }

    .btn-outline:hover {
      border-color: #34D399;
      background-color: var(--surface-card-hover);
      color: #34D399;
      transform: translateY(-2px);
    }

    .btn-large {
      padding: 16px 26px;
      font-size: 16px;
      border-radius: 14px;
    }

    /* Hero Section */
    .hero {
      background: radial-gradient(circle at 50% 0%, rgba(16, 185, 129, 0.22) 0%, transparent 70%),
                  linear-gradient(180deg, #111C15 0%, #080E0A 100%);
      padding: 50px 20px 36px;
      text-align: center;
      border-bottom: 1px solid var(--border-subtle);
    }

    .hero-content {
      max-width: 860px;
      margin: 0 auto;
    }

    .badge-pill {
      display: inline-flex;
      align-items: center;
      gap: 6px;
      background-color: rgba(16, 185, 129, 0.18);
      color: #34D399;
      padding: 6px 14px;
      border-radius: 50px;
      font-size: 13px;
      font-weight: 700;
      margin-bottom: 18px;
      border: 1px solid rgba(16, 185, 129, 0.35);
    }

    .hero h2 {
      font-size: 40px;
      line-height: 1.2;
      font-weight: 800;
      color: #FFFFFF;
      letter-spacing: -0.8px;
      margin-bottom: 16px;
    }

    .hero h2 span {
      background: linear-gradient(135deg, #10B981, #34D399);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }

    .hero p {
      font-size: 17px;
      color: #D1D5DB;
      margin-bottom: 30px;
      max-width: 720px;
      margin-left: auto;
      margin-right: auto;
      line-height: 1.6;
    }

    .cta-container {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      gap: 14px;
      margin-bottom: 20px;
    }

    .download-meta {
      font-size: 13px;
      color: #9CA3AF;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 16px;
      margin-top: 10px;
      flex-wrap: wrap;
    }

    /* Direct Access Toolbar (Helpline, WhatsApp, Email, Maps) */
    .quick-bar-wrapper {
      max-width: 1060px;
      margin: -20px auto 40px;
      padding: 0 20px;
      position: relative;
      z-index: 10;
    }

    .quick-bar {
      background: var(--surface);
      border-radius: var(--radius);
      padding: 16px 20px;
      box-shadow: var(--shadow-md);
      border: 1px solid var(--border-subtle);
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 14px;
    }

    .quick-card {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 10px 14px;
      border-radius: 12px;
      background-color: var(--surface-card);
      text-decoration: none;
      color: #FFFFFF;
      transition: all 0.2s;
      border: 1px solid var(--border-subtle);
    }

    .quick-card:hover {
      background-color: var(--surface-card-hover);
      border-color: #10B981;
      transform: translateY(-2px);
    }

    .icon-box {
      width: 40px;
      height: 40px;
      border-radius: 10px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 18px;
    }

    .icon-call { background: rgba(16, 185, 129, 0.2); color: #34D399; }
    .icon-wa { background: rgba(37, 211, 102, 0.2); color: #25D366; }
    .icon-telegram { background: rgba(2, 136, 209, 0.2); color: #38BDF8; }
    .icon-email { background: rgba(230, 81, 0, 0.2); color: #FB923C; }
    .icon-maps { background: rgba(81, 45, 168, 0.2); color: #A78BFA; }

    .quick-card-text span {
      font-size: 11px;
      color: #9CA3AF;
      display: block;
      text-transform: uppercase;
      font-weight: 600;
    }

    .quick-card-text strong {
      font-size: 13px;
      color: #FFFFFF;
      display: block;
    }

    /* Purpose Highlights Grid */
    .purpose-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
      gap: 18px;
      max-width: 1060px;
      margin: 0 auto 50px;
      padding: 0 20px;
    }

    .purpose-card {
      background: var(--surface-card);
      border: 1px solid var(--border-subtle);
      border-radius: var(--radius);
      padding: 22px;
      box-shadow: var(--shadow-sm);
      transition: transform 0.2s, box-shadow 0.2s, border-color 0.2s;
    }

    .purpose-card:hover {
      transform: translateY(-3px);
      box-shadow: var(--shadow-md);
      border-color: #10B981;
      background: var(--surface-card-hover);
    }

    .purpose-card .icon {
      font-size: 32px;
      margin-bottom: 12px;
      display: block;
    }

    .purpose-card h3,
    .purpose-card h4 {
      font-size: 17px;
      font-weight: 700;
      color: #FFFFFF;
      margin-bottom: 8px;
    }

    .purpose-card p {
      font-size: 13.5px;
      color: #D1D5DB;
      line-height: 1.5;
    }

    .purpose-card .budget-tag {
      margin-top: 12px;
      display: inline-block;
      font-size: 11.5px;
      font-weight: 700;
      color: #34D399;
      background-color: rgba(16, 185, 129, 0.2);
      padding: 3px 8px;
      border-radius: 6px;
      border: 1px solid rgba(16, 185, 129, 0.3);
    }

    /* Testimonials Section (Medical, Legal, Student, Tourist) */
    .testimonials-section {
      max-width: 1060px;
      margin: 0 auto 55px;
      padding: 0 20px;
    }

    .testimonials-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(310px, 1fr));
      gap: 20px;
    }

    .testimonial-card {
      background: var(--surface-card);
      border: 1px solid var(--border-subtle);
      border-radius: var(--radius);
      padding: 24px;
      box-shadow: var(--shadow-sm);
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      position: relative;
      transition: transform 0.2s, box-shadow 0.2s, border-color 0.2s;
    }

    .testimonial-card:hover {
      transform: translateY(-3px);
      box-shadow: var(--shadow-md);
      border-color: #10B981;
    }

    .testimonial-header {
      display: flex;
      align-items: center;
      gap: 12px;
      margin-bottom: 12px;
    }

    .testimonial-avatar {
      width: 48px;
      height: 48px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 800;
      font-size: 16px;
      color: #FFFFFF;
      flex-shrink: 0;
    }

    .avatar-medical { background: linear-gradient(135deg, #0288D1, #01579B); }
    .avatar-legal { background: linear-gradient(135deg, #455A64, #263238); }
    .avatar-student { background: linear-gradient(135deg, #7B1FA2, #4A148C); }
    .avatar-transit { background: linear-gradient(135deg, #10B981, #059669); }

    .testimonial-user-info h5 {
      font-size: 15px;
      font-weight: 700;
      color: #FFFFFF;
      margin-bottom: 2px;
    }

    .testimonial-user-info span {
      font-size: 12px;
      color: #9CA3AF;
      display: block;
    }

    .testimonial-badge {
      display: inline-flex;
      align-items: center;
      gap: 4px;
      font-size: 11px;
      font-weight: 700;
      padding: 3px 8px;
      border-radius: 6px;
      margin-bottom: 12px;
      align-self: flex-start;
    }

    .badge-med { background: rgba(2, 136, 209, 0.2); color: #38BDF8; }
    .badge-law { background: rgba(148, 163, 184, 0.2); color: #CBD5E1; }
    .badge-stu { background: rgba(168, 85, 247, 0.2); color: #C084FC; }
    .badge-tour { background: rgba(16, 185, 129, 0.2); color: #34D399; }

    .testimonial-stars {
      color: #F59E0B;
      font-size: 14px;
      letter-spacing: 2px;
      margin-bottom: 10px;
    }

    .testimonial-quote {
      font-size: 13.5px;
      color: #E5E7EB;
      line-height: 1.55;
      font-style: italic;
      margin-bottom: 16px;
      position: relative;
    }

    .testimonial-footer {
      border-top: 1px dashed var(--border-subtle);
      padding-top: 12px;
      margin-top: auto;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      gap: 8px;
      font-size: 11.5px;
      color: #9CA3AF;
    }

    .testimonial-pill {
      background: #080E0A;
      padding: 3px 8px;
      border-radius: 6px;
      font-weight: 600;
      color: #34D399;
      border: 1px solid var(--border-subtle);
    }

    /* Verified Partners Section */
    .partners-section {
      max-width: 1060px;
      margin: 0 auto 55px;
      padding: 0 20px;
    }

    .partners-category-title {
      font-size: 12px;
      font-weight: 700;
      text-transform: uppercase;
      letter-spacing: 0.8px;
      color: #6EE7B7;
      margin: 22px 0 12px;
      display: flex;
      align-items: center;
      gap: 8px;
    }

    .partners-category-title::after {
      content: '';
      flex: 1;
      height: 1px;
      background: var(--border-subtle);
    }

    .partners-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
      gap: 16px;
    }

    .partner-card {
      background: var(--surface-card);
      border: 1px solid var(--border-subtle);
      border-radius: 14px;
      padding: 16px;
      display: flex;
      align-items: center;
      gap: 14px;
      box-shadow: var(--shadow-sm);
      transition: all 0.2s ease;
    }

    .partner-card:hover {
      transform: translateY(-2px);
      box-shadow: var(--shadow-md);
      border-color: #10B981;
      background: var(--surface-card-hover);
    }

    .partner-logo-box {
      width: 52px;
      height: 52px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-shrink: 0;
      font-weight: 900;
      font-size: 15px;
      letter-spacing: -0.5px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.3);
    }

    .logo-indrive { background: #7BDE2A; color: #0B2404; font-family: 'Inter', system-ui, sans-serif; }
    .logo-yango { background: #FF0000; color: #FFFFFF; font-family: 'Inter', system-ui, sans-serif; }
    .logo-metro { background: #00875A; color: #FFFFFF; }
    .logo-cda-ev { background: #00B8D9; color: #FFFFFF; }
    .logo-serena { background: #1A1A1A; color: #D4AF37; border: 1.5px solid #D4AF37; }
    .logo-marriott { background: #8B0000; color: #FFFFFF; }
    .logo-centaurus { background: #0F2027; color: #F8B195; }
    .logo-guesthouse { background: #10B981; color: #FFFFFF; }
    .logo-4x4 { background: #34495E; color: #F39C12; }
    .logo-hostels { background: #5E35B1; color: #FFFFFF; }

    .partner-info h5 {
      font-size: 14px;
      font-weight: 700;
      color: #FFFFFF;
      margin-bottom: 2px;
      display: flex;
      align-items: center;
      gap: 6px;
    }

    .partner-info .partner-type {
      font-size: 11.5px;
      color: #9CA3AF;
      display: block;
      margin-bottom: 4px;
    }

    .partner-info .partner-status {
      font-size: 10px;
      font-weight: 700;
      color: #34D399;
      background: rgba(16, 185, 129, 0.18);
      padding: 2px 6px;
      border-radius: 4px;
      display: inline-flex;
      align-items: center;
      gap: 3px;
    }

    /* Dynamic QR Code Showcase */
    .qr-showcase-section {
      background: linear-gradient(180deg, #111C15 0%, #0A110D 100%);
      border: 1.5px solid var(--border-subtle);
      border-radius: 24px;
      padding: 36px 28px;
      max-width: 1060px;
      margin: 0 auto 50px;
      box-shadow: var(--shadow-md);
    }

    .qr-grid {
      display: grid;
      grid-template-columns: 280px 1fr;
      gap: 36px;
      align-items: center;
    }

    @media (max-width: 820px) {
      .qr-grid {
        grid-template-columns: 1fr;
        text-align: center;
      }
    }

    .qr-card {
      background: var(--surface-card);
      border-radius: 20px;
      padding: 20px;
      box-shadow: var(--shadow-lg);
      border: 1.5px solid var(--border-subtle);
      text-align: center;
    }

    .qr-scanner-tag {
      background: #10B981;
      color: #fff;
      font-size: 11px;
      font-weight: 800;
      text-transform: uppercase;
      letter-spacing: 0.5px;
      padding: 5px 12px;
      border-radius: 20px;
      display: inline-block;
      margin-bottom: 12px;
    }

    .qr-code-frame {
      background: #FFFFFF;
      padding: 10px;
      border-radius: 14px;
      border: 1px solid #ECEEEF;
      display: inline-block;
      box-shadow: 0 4px 12px rgba(0,0,0,0.3);
    }

    .qr-code-img {
      width: 200px;
      height: 200px;
      display: block;
      border-radius: 8px;
    }

    .qr-live-pulse {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 6px;
      font-size: 11.5px;
      font-weight: 700;
      color: #34D399;
      margin-top: 10px;
    }

    .pulse-dot {
      width: 8px;
      height: 8px;
      border-radius: 50%;
      background-color: #25D366;
      animation: pulseAnim 1.6s infinite ease-in-out;
    }

    @keyframes pulseAnim {
      0% { transform: scale(0.9); opacity: 0.6; }
      50% { transform: scale(1.3); opacity: 1; }
      100% { transform: scale(0.9); opacity: 0.6; }
    }

    .qr-mode-tabs {
      display: inline-flex;
      background: #080E0A;
      padding: 4px;
      border-radius: 12px;
      gap: 4px;
      margin: 14px 0;
      border: 1px solid var(--border-subtle);
    }

    .qr-tab-btn {
      border: none;
      padding: 8px 16px;
      border-radius: 8px;
      font-size: 12.5px;
      font-weight: 700;
      cursor: pointer;
      background: transparent;
      color: #9CA3AF;
      transition: all 0.2s;
    }

    .qr-tab-btn.active {
      background: #10B981;
      color: #FFFFFF;
      box-shadow: 0 2px 8px rgba(16, 185, 129, 0.4);
    }

    .qr-steps-list {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      gap: 10px;
      list-style: none;
      margin: 16px 0;
    }

    @media (max-width: 600px) {
      .qr-steps-list {
        grid-template-columns: 1fr;
      }
    }

    .qr-step-box {
      background: var(--surface-card);
      border: 1px solid var(--border-subtle);
      border-radius: 12px;
      padding: 12px;
      display: flex;
      gap: 10px;
      align-items: flex-start;
      text-align: left;
    }

    .qr-step-num {
      width: 24px;
      height: 24px;
      background: #10B981;
      color: #fff;
      font-size: 12px;
      font-weight: 800;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-shrink: 0;
    }

    .qr-step-text strong {
      font-size: 12.5px;
      display: block;
      color: #FFFFFF;
    }

    .qr-step-text span {
      font-size: 11px;
      color: #9CA3AF;
      line-height: 1.3;
      display: block;
    }

    .qr-url-box {
      display: flex;
      align-items: center;
      background: #080E0A;
      border: 1.5px solid var(--border-subtle);
      border-radius: 12px;
      padding: 6px 10px 6px 14px;
      gap: 10px;
      max-width: 580px;
      margin: 14px 0 10px;
    }

    .qr-url-text {
      flex: 1;
      font-family: monospace;
      font-size: 12px;
      color: #34D399;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      text-align: left;
    }

    .qr-copy-btn {
      background: #10B981;
      color: #fff;
      border: none;
      padding: 8px 14px;
      border-radius: 8px;
      font-size: 12px;
      font-weight: 700;
      cursor: pointer;
      display: inline-flex;
      align-items: center;
      gap: 4px;
      transition: background 0.2s;
      flex-shrink: 0;
    }

    .qr-badges-row {
      display: flex;
      flex-wrap: wrap;
      gap: 8px;
      margin-top: 10px;
    }

    .qr-badge {
      font-size: 11px;
      background: rgba(16, 185, 129, 0.18);
      color: #34D399;
      padding: 4px 10px;
      border-radius: 6px;
      font-weight: 600;
      border: 1px solid rgba(16, 185, 129, 0.3);
    }

    /* Channels & Forums */
    .section-title {
      text-align: center;
      max-width: 700px;
      margin: 0 auto 30px;
      padding: 0 20px;
    }

    .section-title h2,
    .section-title h3 {
      font-size: 28px;
      color: #FFFFFF;
      font-weight: 800;
      margin-bottom: 8px;
    }

    .section-title p {
      font-size: 15px;
      color: #9CA3AF;
    }

    .channels-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
      gap: 14px;
      max-width: 1060px;
      margin: 0 auto 50px;
      padding: 0 20px;
    }

    .channel-item {
      background: var(--surface-card);
      border: 1px solid var(--border-subtle);
      border-radius: var(--radius);
      padding: 16px;
      text-decoration: none;
      color: #FFFFFF;
      display: flex;
      flex-direction: column;
      align-items: center;
      text-align: center;
      transition: all 0.2s;
    }

    .channel-item:hover {
      transform: translateY(-2px);
      box-shadow: var(--shadow-sm);
      border-color: #10B981;
      background: var(--surface-card-hover);
    }

    .channel-icon {
      width: 44px;
      height: 44px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 20px;
      margin-bottom: 8px;
    }

    .channel-name {
      font-size: 13px;
      font-weight: 700;
      color: #FFFFFF;
    }

    .channel-action {
      font-size: 11px;
      color: #6EE7B7;
    }

    /* Frequently Asked Questions (Accordion) */
    .faq-section {
      max-width: 960px;
      margin: 0 auto 50px;
      padding: 0 20px;
    }

    .faq-accordion {
      display: flex;
      flex-direction: column;
      gap: 12px;
    }

    .faq-item {
      background: var(--surface-card);
      border: 1px solid var(--border-subtle);
      border-radius: var(--radius);
      overflow: hidden;
      transition: all 0.25s ease;
      box-shadow: var(--shadow-sm);
    }

    .faq-item.active {
      border-color: #10B981;
      box-shadow: 0 4px 14px rgba(16, 185, 129, 0.15);
    }

    .faq-question-btn {
      width: 100%;
      background: none;
      border: none;
      padding: 18px 20px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      text-align: left;
      cursor: pointer;
      font-size: 15.5px;
      font-weight: 700;
      color: #FFFFFF;
      gap: 12px;
    }

    .faq-question-content {
      display: flex;
      align-items: center;
      gap: 12px;
      flex: 1;
    }

    .faq-cat-badge {
      font-size: 11px;
      font-weight: 700;
      padding: 3px 8px;
      border-radius: 6px;
      white-space: nowrap;
    }

    .badge-logistics { background: rgba(2, 136, 209, 0.2); color: #38BDF8; }
    .badge-visa { background: rgba(148, 163, 184, 0.2); color: #CBD5E1; }
    .badge-emergency { background: rgba(239, 68, 68, 0.2); color: #F87171; }
    .badge-general { background: rgba(16, 185, 129, 0.2); color: #34D399; }

    .faq-chevron {
      font-size: 14px;
      color: #9CA3AF;
      transition: transform 0.25s ease;
    }

    .faq-item.active .faq-chevron {
      transform: rotate(180deg);
      color: #10B981;
    }

    .faq-answer {
      display: none;
      padding: 0 20px 20px 20px;
      border-top: 1px solid var(--border-subtle);
      font-size: 14px;
      line-height: 1.65;
      color: #D1D5DB;
      background: #0B130E;
    }

    .faq-item.active .faq-answer {
      display: block;
      padding-top: 14px;
    }

    .faq-bullet-box {
      background: var(--surface-card);
      border: 1px solid var(--border-subtle);
      border-radius: 8px;
      padding: 12px 14px;
      margin-top: 12px;
      font-size: 13.5px;
      color: #E5E7EB;
    }

    .faq-bullet-box ul {
      margin: 6px 0 0 16px;
      padding: 0;
    }

    .faq-bullet-box li {
      margin-bottom: 4px;
    }

    /* Footer */
    footer {
      background-color: #060B08;
      border-top: 1px solid var(--border-subtle);
      color: #9CA3AF;
      padding: 40px 20px 30px;
      margin-top: auto;
      text-align: center;
      font-size: 13px;
    }

    .footer-links {
      display: flex;
      justify-content: center;
      gap: 20px;
      margin-bottom: 18px;
      flex-wrap: wrap;
    }

    .footer-links a {
      color: #FFFFFF;
      text-decoration: none;
      font-weight: 500;
      transition: color 0.2s;
    }

    .footer-links a:hover {
      color: #10B981;
    }

    /* Social Media Follow Bar */
    .social-follow-bar {
      background: var(--surface-card);
      border: 1px solid var(--border-subtle);
      border-radius: 16px;
      padding: 16px 20px;
      margin: 20px auto 36px;
      max-width: 1080px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      flex-wrap: wrap;
      gap: 14px;
      box-shadow: var(--shadow-sm);
    }

    .social-follow-title {
      display: flex;
      align-items: center;
      gap: 10px;
      font-size: 14px;
      font-weight: 700;
      color: #FFFFFF;
    }

    .social-follow-title span.handle-tag {
      background: rgba(16, 185, 129, 0.18);
      color: #34D399;
      padding: 3px 10px;
      border-radius: 20px;
      font-size: 12px;
      font-weight: 600;
      border: 1px solid rgba(16, 185, 129, 0.3);
    }

    .social-follow-icons {
      display: flex;
      flex-wrap: wrap;
      gap: 8px;
    }

    .social-btn {
      display: inline-flex;
      align-items: center;
      gap: 6px;
      padding: 7px 12px;
      border-radius: 10px;
      font-size: 12.5px;
      font-weight: 600;
      text-decoration: none;
      transition: all 0.2s ease;
      border: 1px solid var(--border-subtle);
      background: #0A110D;
      color: #E5E7EB;
    }

    .social-btn:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0,0,0,0.4);
      color: #FFFFFF;
    }

    .social-btn.btn-ig:hover { background: #E1306C; border-color: #E1306C; }
    .social-btn.btn-fb:hover { background: #1877F2; border-color: #1877F2; }
    .social-btn.btn-x:hover { background: #000000; border-color: #FFFFFF; }
    .social-btn.btn-yt:hover { background: #FF0000; border-color: #FF0000; }
    .social-btn.btn-tt:hover { background: #010101; border-color: #34D399; }
    .social-btn.btn-li:hover { background: #0A66C2; border-color: #0A66C2; }
    .social-btn.btn-wa:hover { background: #25D366; border-color: #25D366; }
    .social-btn.btn-tg:hover { background: #0088CC; border-color: #0088CC; }
    .social-btn.btn-mail:hover { background: #10B981; border-color: #10B981; }

    /* Join Our Team / Provider & Caregiver Section */
    .join-team-section {
      background: linear-gradient(180deg, #111C15 0%, #0A110D 100%);
      border: 1px solid var(--border-subtle);
      border-radius: 24px;
      padding: 44px 28px;
      margin: 40px auto;
      max-width: 1120px;
      position: relative;
      overflow: hidden;
    }

    .provider-tracks-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(310px, 1fr));
      gap: 22px;
      margin: 28px 0;
    }

    .provider-track-card {
      background: var(--surface-card);
      border: 1px solid var(--border-subtle);
      border-radius: 16px;
      padding: 24px 20px;
      display: flex;
      flex-direction: column;
      box-shadow: var(--shadow-sm);
      transition: transform 0.25s, box-shadow 0.25s, border-color 0.25s;
    }

    .provider-track-card:hover {
      transform: translateY(-3px);
      box-shadow: var(--shadow-md);
      border-color: #10B981;
      background: var(--surface-card-hover);
    }

    .track-icon-badge {
      width: 48px;
      height: 48px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 24px;
      margin-bottom: 14px;
    }

    .provider-track-card h3 {
      font-size: 18px;
      font-weight: 700;
      color: #FFFFFF;
      margin-bottom: 8px;
    }

    .provider-track-card p {
      font-size: 13.5px;
      line-height: 1.55;
      color: #D1D5DB;
      margin-bottom: 16px;
    }

    .track-perks-list {
      list-style: none;
      padding: 0;
      margin: 0 0 18px 0;
      font-size: 13px;
      color: #E5E7EB;
    }

    .track-perks-list li {
      margin-bottom: 7px;
      display: flex;
      align-items: flex-start;
      gap: 8px;
    }

    .track-perks-list li span.check {
      color: #34D399;
      font-weight: 700;
    }

    .provider-form-wrapper {
      background: #111C15;
      border: 1px solid var(--border-subtle);
      border-radius: 18px;
      padding: 28px 24px;
      margin-top: 24px;
      box-shadow: var(--shadow-sm);
    }

    .provider-form-header {
      text-align: center;
      max-width: 620px;
      margin: 0 auto 22px;
    }

    .provider-form-header h4 {
      font-size: 20px;
      font-weight: 800;
      color: #FFFFFF;
      margin-bottom: 6px;
    }

    .provider-form-header p {
      font-size: 13.5px;
      color: #9CA3AF;
    }

    .provider-form-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
      gap: 16px;
    }

    .form-group {
      display: flex;
      flex-direction: column;
      gap: 6px;
    }

    .form-group label {
      font-size: 12.5px;
      font-weight: 700;
      color: #FFFFFF;
    }

    .form-group input,
    .form-group select,
    .form-group textarea {
      padding: 10px 14px;
      border: 1.5px solid var(--border-subtle);
      border-radius: 10px;
      font-size: 14px;
      font-family: inherit;
      background: #0A110D;
      color: #FFFFFF;
      outline: none;
      transition: border-color 0.2s, box-shadow 0.2s;
    }

    .form-group input:focus,
    .form-group select:focus,
    .form-group textarea:focus {
      border-color: #10B981;
      box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.25);
      background: #0F1A13;
    }

    .form-actions-row {
      display: flex;
      flex-wrap: wrap;
      gap: 12px;
      margin-top: 20px;
      justify-content: flex-end;
    }

    .btn-apply-wa {
      background: #25D366;
      color: #FFFFFF;
      border: none;
      border-radius: 12px;
      padding: 12px 24px;
      font-size: 14px;
      font-weight: 700;
      cursor: pointer;
      display: inline-flex;
      align-items: center;
      gap: 8px;
      transition: all 0.2s;
    }

    .btn-apply-wa:hover {
      background: #1EBE5D;
      transform: translateY(-1px);
      box-shadow: 0 4px 14px rgba(37,211,102,0.45);
    }

    .btn-apply-email {
      background: linear-gradient(135deg, #10B981, #059669);
      color: #FFFFFF;
      border: none;
      border-radius: 12px;
      padding: 12px 24px;
      font-size: 14px;
      font-weight: 700;
      cursor: pointer;
      display: inline-flex;
      align-items: center;
      gap: 8px;
      transition: all 0.2s;
    }

    .btn-apply-email:hover {
      background: #059669;
      transform: translateY(-1px);
      box-shadow: 0 4px 14px rgba(16, 185, 129, 0.35);
    }

    /* Gemini Live Chatbot Floating Widget & Modal */
    .gemini-fab {
      position: fixed;
      bottom: 24px;
      right: 24px;
      z-index: 9999;
      background: linear-gradient(135deg, #10B981 0%, #059669 60%, #047857 100%);
      color: #FFFFFF;
      border: none;
      border-radius: 32px;
      padding: 12px 20px;
      font-size: 14px;
      font-weight: 700;
      cursor: pointer;
      display: flex;
      align-items: center;
      gap: 10px;
      box-shadow: 0 10px 28px rgba(16, 185, 129, 0.45);
      transition: all 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275);
    }

    .gemini-fab:hover {
      transform: translateY(-3px) scale(1.03);
      box-shadow: 0 14px 34px rgba(16, 185, 129, 0.6);
    }

    .gemini-fab .fab-sparkle {
      font-size: 18px;
      animation: sparkleRotate 3s infinite linear;
    }

    @keyframes sparkleRotate {
      0% { transform: scale(1) rotate(0deg); }
      50% { transform: scale(1.2) rotate(180deg); }
      100% { transform: scale(1) rotate(360deg); }
    }

    .gemini-fab .pulse-ring {
      position: absolute;
      top: -3px;
      right: -3px;
      width: 12px;
      height: 12px;
      background: #25D366;
      border-radius: 50%;
      border: 2px solid #FFF;
      box-shadow: 0 0 8px #25D366;
    }

    /* Chat Modal */
    .gemini-chat-modal {
      position: fixed;
      bottom: 84px;
      right: 24px;
      width: 410px;
      max-width: calc(100vw - 32px);
      height: 600px;
      max-height: calc(100vh - 110px);
      background: #111C15;
      border-radius: 20px;
      box-shadow: 0 20px 50px rgba(0,0,0,0.7);
      border: 1px solid var(--border-subtle);
      display: none;
      flex-direction: column;
      z-index: 10000;
      overflow: hidden;
      animation: modalFadeIn 0.25s ease-out;
    }

    .gemini-chat-modal.active {
      display: flex;
    }

    @keyframes modalFadeIn {
      from { opacity: 0; transform: translateY(16px) scale(0.96); }
      to { opacity: 1; transform: translateY(0) scale(1); }
    }

    .gemini-header {
      background: linear-gradient(135deg, #09120D 0%, #11261B 100%);
      color: #FFFFFF;
      padding: 14px 16px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      gap: 10px;
      border-bottom: 1px solid var(--border-subtle);
    }

    .gemini-header-left {
      display: flex;
      align-items: center;
      gap: 8px;
    }

    .gemini-avatar {
      width: 34px;
      height: 34px;
      border-radius: 10px;
      background: rgba(16, 185, 129, 0.25);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 18px;
      border: 1px solid rgba(16, 185, 129, 0.4);
    }

    .gemini-title {
      font-size: 14px;
      font-weight: 700;
      line-height: 1.2;
      color: #FFFFFF;
    }

    .gemini-sub {
      font-size: 11px;
      color: #6EE7B7;
      display: flex;
      align-items: center;
      gap: 4px;
    }

    .gemini-sub span.online-dot {
      width: 6px;
      height: 6px;
      border-radius: 50%;
      background: #25D366;
      display: inline-block;
    }

    .gemini-header-controls {
      display: flex;
      align-items: center;
      gap: 6px;
    }

    .lang-select-box {
      background: #080E0A;
      color: #FFFFFF;
      border: 1px solid var(--border-subtle);
      border-radius: 8px;
      padding: 4px 8px;
      font-size: 11.5px;
      font-weight: 600;
      cursor: pointer;
      outline: none;
    }

    .lang-select-box option {
      background: #080E0A;
      color: #FFF;
    }

    .gemini-close-btn {
      background: rgba(255,255,255,0.1);
      border: none;
      color: #FFFFFF;
      width: 28px;
      height: 28px;
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      font-size: 16px;
      transition: background 0.2s;
    }

    .gemini-close-btn:hover {
      background: rgba(255,255,255,0.25);
    }

    .gemini-suggestions {
      padding: 10px 14px 6px;
      background: #0B130E;
      border-bottom: 1px solid var(--border-subtle);
      display: flex;
      gap: 6px;
      overflow-x: auto;
      white-space: nowrap;
      scrollbar-width: thin;
    }

    .gemini-chip {
      background: var(--surface-card);
      border: 1px solid var(--border-subtle);
      border-radius: 14px;
      padding: 5px 10px;
      font-size: 11.5px;
      font-weight: 500;
      color: #34D399;
      cursor: pointer;
      flex-shrink: 0;
      transition: all 0.2s;
    }

    .gemini-chip:hover {
      background: var(--surface-card-hover);
      border-color: #10B981;
      color: #FFFFFF;
    }

    .gemini-messages {
      flex: 1;
      padding: 14px;
      overflow-y: auto;
      display: flex;
      flex-direction: column;
      gap: 12px;
      background: #080E0A;
      font-size: 13.5px;
    }

    .msg-bubble {
      max-width: 86%;
      padding: 10px 14px;
      border-radius: 14px;
      line-height: 1.5;
      word-break: break-word;
    }

    .msg-user {
      align-self: flex-end;
      background: linear-gradient(135deg, #10B981, #059669);
      color: #FFFFFF;
      border-bottom-right-radius: 4px;
    }

    .msg-bot {
      align-self: flex-start;
      background: var(--surface-card);
      color: #FFFFFF;
      border: 1px solid var(--border-subtle);
      border-bottom-left-radius: 4px;
      box-shadow: 0 2px 6px rgba(0,0,0,0.3);
    }

    .msg-bot-actions {
      display: flex;
      align-items: center;
      gap: 8px;
      margin-top: 6px;
      padding-top: 6px;
      border-top: 1px dashed var(--border-subtle);
      font-size: 11px;
    }

    .btn-msg-listen {
      background: #0D1711;
      border: 1px solid var(--border-subtle);
      border-radius: 6px;
      padding: 2px 7px;
      font-size: 11px;
      cursor: pointer;
      color: #34D399;
      display: inline-flex;
      align-items: center;
      gap: 4px;
    }

    .btn-msg-listen:hover {
      background: var(--surface-card-hover);
      color: #FFFFFF;
    }

    .gemini-voice-bar {
      display: none;
      background: #1F1315;
      border-top: 1px solid #7F1D1D;
      padding: 8px 14px;
      font-size: 12px;
      color: #F87171;
      justify-content: space-between;
      align-items: center;
    }

    .voice-pulse-dot {
      width: 10px;
      height: 10px;
      background-color: #EF4444;
      border-radius: 50%;
      display: inline-block;
      animation: voicePulse 0.8s infinite alternate;
    }

    @keyframes voicePulse {
      from { transform: scale(0.8); opacity: 0.5; }
      to { transform: scale(1.3); opacity: 1; }
    }

    .gemini-input-box {
      padding: 10px 12px;
      background: #111C15;
      border-top: 1px solid var(--border-subtle);
      display: flex;
      align-items: center;
      gap: 8px;
    }

    .gemini-input {
      flex: 1;
      padding: 9px 14px;
      border: 1.5px solid var(--border-subtle);
      border-radius: 20px;
      font-size: 13.5px;
      outline: none;
      background: #080E0A;
      color: #FFFFFF;
      transition: border-color 0.2s;
    }

    .gemini-input:focus {
      border-color: #10B981;
    }

    .btn-mic {
      background: #080E0A;
      border: 1.5px solid var(--border-subtle);
      color: #34D399;
      width: 36px;
      height: 36px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      font-size: 16px;
      transition: all 0.2s;
    }

    .btn-mic:hover {
      background: var(--surface-card-hover);
      border-color: #10B981;
      color: #FFFFFF;
    }

    .btn-mic.recording {
      background: #DC2626;
      border-color: #B91C1C;
      color: #FFF;
      animation: voicePulse 1s infinite alternate;
    }

    .btn-send {
      background: #10B981;
      border: none;
      color: #FFFFFF;
      width: 36px;
      height: 36px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      font-size: 16px;
      transition: background 0.2s;
    }

    .btn-send:hover {
      background: #059669;
    }
  </style>"""

start_style = html.find("<style>")
end_style = html.find("</style>") + len("</style>")

if start_style != -1 and end_style != -1:
    html = html[:start_style] + dark_css + html[end_style:]

# Update inline styles for dark theme
html = html.replace('style="font-size: clamp(32px, 5vw, 48px); font-weight: 800; line-height: 1.15; margin-bottom: 12px; color: var(--primary-dark);"',
                    'style="font-size: clamp(32px, 5vw, 48px); font-weight: 800; line-height: 1.15; margin-bottom: 12px; color: #FFFFFF;"')

html = html.replace('<span style="color: var(--primary);">Islamabad</span>',
                    '<span style="color: #10B981;">Islamabad</span>')

html = html.replace('<button onclick="openIosModal()" class="btn btn-outline btn-large" style="border-color: #000; color: #000;">',
                    '<button onclick="openIosModal()" class="btn btn-outline btn-large" style="border-color: #10B981; color: #FFFFFF;">')

html = html.replace('style="font-size: 26px; color: var(--primary-dark); font-weight: 800; letter-spacing: -0.5px; margin: 10px 0 6px;"',
                    'style="font-size: 26px; color: #FFFFFF; font-weight: 800; letter-spacing: -0.5px; margin: 10px 0 6px;"')

html = html.replace('<div class="track-icon-badge" style="background: #E8F5E9; color: #2E7D32;">🚗</div>',
                    '<div class="track-icon-badge" style="background: rgba(16, 185, 129, 0.2); color: #34D399;">🚗</div>')

html = html.replace('<div class="track-icon-badge" style="background: #E0F2FE; color: #0284C7;">🏡</div>',
                    '<div class="track-icon-badge" style="background: rgba(2, 136, 209, 0.2); color: #38BDF8;">🏡</div>')

html = html.replace('<div class="track-icon-badge" style="background: #FEF3C7; color: #D97706;">🩺</div>',
                    '<div class="track-icon-badge" style="background: rgba(245, 158, 11, 0.2); color: #FBBF24;">🩺</div>')

html = html.replace('background: #ECFDF5; color: #065F46; border: 1px solid #A7F3D0;',
                    'background: rgba(16, 185, 129, 0.15); color: #34D399; border: 1px solid rgba(16, 185, 129, 0.3);')

# Channel icons
html = html.replace('<div class="channel-icon" style="background: #E8F5E9; color: #2E7D32;">💬</div>',
                    '<div class="channel-icon" style="background: rgba(37, 211, 102, 0.2); color: #25D366;">💬</div>')
html = html.replace('<div class="channel-icon" style="background: #E1F5FE; color: #0288D1;">✈️</div>',
                    '<div class="channel-icon" style="background: rgba(2, 136, 209, 0.2); color: #38BDF8;">✈️</div>')
html = html.replace('<div class="channel-icon" style="background: #FFF3E0; color: #E65100;">✉️</div>',
                    '<div class="channel-icon" style="background: rgba(245, 158, 11, 0.2); color: #FB923C;">✉️</div>')
html = html.replace('<div class="channel-icon" style="background: #FCE4EC; color: #E1306C;">📸</div>',
                    '<div class="channel-icon" style="background: rgba(225, 48, 108, 0.2); color: #F472B6;">📸</div>')
html = html.replace('<div class="channel-icon" style="background: #E8EAF6; color: #1877F2;">📘</div>',
                    '<div class="channel-icon" style="background: rgba(24, 119, 242, 0.2); color: #60A5FA;">📘</div>')
html = html.replace('<div class="channel-icon" style="background: #F1F5F9; color: #0F172A;">𝕏</div>',
                    '<div class="channel-icon" style="background: rgba(255, 255, 255, 0.15); color: #FFFFFF;">𝕏</div>')
html = html.replace('<div class="channel-icon" style="background: #FEE2E2; color: #DC2626;">▶️</div>',
                    '<div class="channel-icon" style="background: rgba(220, 38, 38, 0.2); color: #F87171;">▶️</div>')
html = html.replace('<div class="channel-icon" style="background: #F1F5F9; color: #000000;">🎵</div>',
                    '<div class="channel-icon" style="background: rgba(255, 255, 255, 0.15); color: #FFFFFF;">🎵</div>')
html = html.replace('<div class="channel-icon" style="background: #E0F2FE; color: #0284C7;">💼</div>',
                    '<div class="channel-icon" style="background: rgba(10, 102, 194, 0.2); color: #38BDF8;">💼</div>')
html = html.replace('<div class="channel-icon" style="background: #E8F5E9; color: #34A853;">📹</div>',
                    '<div class="channel-icon" style="background: rgba(52, 168, 83, 0.2); color: #34D399;">📹</div>')

# Live app section
html = html.replace('style="background: var(--surface); border: 1px solid var(--border-subtle); border-radius: var(--radius); padding: 24px; max-width: 1060px; margin: 0 auto 50px; box-shadow: var(--shadow-sm); text-align: center;"',
                    'style="background: var(--surface); border: 1px solid var(--border-subtle); border-radius: var(--radius); padding: 24px; max-width: 1060px; margin: 0 auto 50px; box-shadow: var(--shadow-md); text-align: center;"')
html = html.replace('style="font-size: 24px; color: var(--primary-dark); margin-bottom: 8px; font-weight: 800;"',
                    'style="font-size: 24px; color: #FFFFFF; margin-bottom: 8px; font-weight: 800;"')

# iOS modal dark theme
html = html.replace('style="background: #FFFFFF; border-radius: 24px; max-width: 480px; width: 100%; padding: 28px; box-shadow: 0 20px 40px rgba(0,0,0,0.25); position: relative; text-align: left;"',
                    'style="background: #111C15; border: 1.5px solid #1E382A; border-radius: 24px; max-width: 480px; width: 100%; padding: 28px; box-shadow: 0 20px 40px rgba(0,0,0,0.7); position: relative; text-align: left;"')
html = html.replace('style="position: absolute; top: 18px; right: 18px; background: #F0F2F1; border: none; border-radius: 50%; width: 36px; height: 36px; cursor: pointer; font-size: 16px; font-weight: bold; color: #5C6E66; display: flex; align-items: center; justify-content: center;"',
                    'style="position: absolute; top: 18px; right: 18px; background: #1B2B21; border: none; border-radius: 50%; width: 36px; height: 36px; cursor: pointer; font-size: 16px; font-weight: bold; color: #FFFFFF; display: flex; align-items: center; justify-content: center;"')
html = html.replace('style="font-size: 20px; color: var(--primary-dark); font-weight: 800; margin: 0;"',
                    'style="font-size: 20px; color: #FFFFFF; font-weight: 800; margin: 0;"')
html = html.replace('style="font-size: 12.5px; color: #2D6A4F; font-weight: 600;"',
                    'style="font-size: 12.5px; color: #34D399; font-weight: 600;"')
html = html.replace('style="background: #F8FAF9; border: 1px solid #E5E7EB; border-radius: 16px; padding: 16px; margin-bottom: 20px;"',
                    'style="background: #15241C; border: 1px solid #1E382A; border-radius: 16px; padding: 16px; margin-bottom: 20px;"')
html = html.replace('style="font-size: 14px; color: #1F2937; line-height: 1.5;"',
                    'style="font-size: 14px; color: #E5E7EB; line-height: 1.5;"')
html = html.replace('<strong style="color: #111827;">',
                    '<strong style="color: #FFFFFF;">')
html = html.replace('<strong style="color: #007AFF;">',
                    '<strong style="color: #34D399;">')
html = html.replace('style="display: inline-flex; align-items: center; justify-content: center; background: #E5E7EB; border-radius: 6px; padding: 2px 7px; font-size: 13px; font-weight: 700; color: #007AFF;"',
                    'style="display: inline-flex; align-items: center; justify-content: center; background: rgba(16, 185, 129, 0.2); border-radius: 6px; padding: 2px 7px; font-size: 13px; font-weight: 700; color: #34D399;"')
html = html.replace('style="display: inline-flex; align-items: center; justify-content: center; background: #E5E7EB; border-radius: 6px; padding: 2px 7px; font-size: 13px;"',
                    'style="display: inline-flex; align-items: center; justify-content: center; background: rgba(16, 185, 129, 0.2); border-radius: 6px; padding: 2px 7px; font-size: 13px;"')

with open("public/index.html", "w", encoding="utf-8") as f:
    f.write(html)

print("SUCCESS: public/index.html converted to Dark Theme!")

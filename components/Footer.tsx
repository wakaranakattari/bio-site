'use client';

import { Github, Send } from 'lucide-react';
import { motion } from 'framer-motion';
import { useLanguage } from './LanguageProvider';

const socialLinks = [
  { icon: Github, href: 'https://github.com/wakaranakattari', label: 'GitHub' },
  { icon: Send, href: 'https://t.me/wakaranakattari', label: 'Telegram' },
];

export function Footer() {
  const { t } = useLanguage();

  return (
    <footer className="mt-20 border-t border-border">
      <div className="container mx-auto px-4 py-8">
        <div className="flex flex-col md:flex-row justify-between items-center gap-6">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            className="text-center md:text-left"
          >
            <div className="text-lg font-mono font-bold mb-2">wkrn</div>
            <p className="text-sm text-muted-foreground">© 2026 {t('footer.rights')}</p>
          </motion.div>

          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.1 }}
            className="flex space-x-3"
          >
            {socialLinks.map((link, index) => (
              <motion.a
                key={link.label}
                href={link.href}
                target="_blank"
                rel="noopener noreferrer"
                initial={{ opacity: 0, scale: 0 }}
                animate={{ opacity: 1, scale: 1 }}
                transition={{ delay: index * 0.1 }}
                whileHover={{ y: -2 }}
                className="p-2.5 rounded-lg card hover:bg-muted transition-all duration-200"
                aria-label={link.label}
              >
                <link.icon className="w-4 h-4" />
              </motion.a>
            ))}
          </motion.div>
        </div>
      </div>
    </footer>
  );
}

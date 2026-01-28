'use client';

import { Github, Send, Mail } from 'lucide-react';
import { motion } from 'framer-motion';
import { useLanguage } from '@/components/LanguageProvider';

export const contactItems = [
  {
    icon: Github,
    title: 'GitHub',
    value: '@wakaranakattari',
    href: 'https://github.com/wakaranakattari',
    color: 'text-gray-800 dark:text-gray-200',
  },
  {
    icon: Send,
    title: 'Telegram',
    value: '@wakaranakattari',
    href: 'https://t.me/wakaranakattari',
    color: 'text-blue-400',
  },
];

export default function ContactClient() {
  const { t } = useLanguage();

  return (
    <div className="container mx-auto px-4 py-12">
      <div className="max-w-4xl mx-auto">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="mb-16"
        >
          <div className="flex items-center gap-4 mb-8">
            <div className="p-3 card">
              <Mail className="w-6 h-6 text-accent" />
            </div>
            <div>
              <h1 className="text-6xl md:text-7xl font-bold font-mono mb-2">
                <span className="block text-foreground">{t('contact.title')}</span>
                <span className="block gradient-text">{t('contact.highlight')}</span>
              </h1>
              <p className="text-lg text-muted-foreground max-w-2xl">{t('contact.description')}</p>
            </div>
          </div>

          <div className="grid md:grid-cols-2 gap-6 mt-12">
            {contactItems.map((item, index) => (
              <motion.a
                key={item.title}
                href={item.href}
                target="_blank"
                rel="noopener noreferrer"
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: index * 0.1 }}
                whileHover={{ y: -5 }}
                className="p-6 rounded-2xl card hover:border-accent/50 transition-all duration-300"
              >
                <div className="flex items-center gap-4 mb-4">
                  <div className={`p-3 rounded-xl ${item.color} bg-opacity-10`}>
                    <item.icon className="w-6 h-6" />
                  </div>
                  <h3 className="text-xl font-bold">{item.title}</h3>
                </div>
                <p className="text-muted-foreground">{item.value}</p>
              </motion.a>
            ))}
          </div>
        </motion.div>
      </div>
    </div>
  );
}

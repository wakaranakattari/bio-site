'use client';

import { Code2, Server, Database, Cpu, MapPin, GraduationCap, Target, User } from 'lucide-react';
import { motion } from 'framer-motion';
import { useLanguage } from '@/components/LanguageProvider';

export default function AboutPage() {
  const { t } = useLanguage();

  const skills = [
    {
      icon: Code2,
      title: 'Frontend',
      items: ['React', 'Next.js 14', 'TypeScript', 'Tailwind CSS'],
      color: 'bg-blue-500/10',
    },
    {
      icon: Server,
      title: 'Backend',
      items: ['Node.js', 'Python', 'PostgreSQL', 'Redis'],
      color: 'bg-purple-500/10',
    },
    {
      icon: Database,
      title: 'Databases',
      items: ['MongoDB', 'MySQL', 'PostgreSQL', 'Elasticsearch'],
      color: 'bg-green-500/10',
    },
    {
      icon: Cpu,
      title: 'Tools',
      items: ['Docker', 'Git', 'VS Code', 'Figma'],
      color: 'bg-orange-500/10',
    },
  ];

  const infoItems = [
    { icon: MapPin, text: t('about.location') },
    { icon: GraduationCap, text: t('about.education') },
    { icon: Target, text: t('about.focus') },
  ];

  return (
    <div className="container mx-auto px-4 py-12 relative">
      <div className="max-w-6xl mx-auto">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="mb-16"
        >
          <div className="flex items-center gap-4 mb-8">
            <div className="p-3 card">
              <User className="w-6 h-6 text-accent" />
            </div>
            <h1 className="text-6xl md:text-7xl font-bold font-mono">
              <span className="block text-foreground">{t('about.title')}</span>
              <span className="block gradient-text">{t('about.highlight')}</span>
            </h1>
          </div>

          <div className="space-y-6 mb-12">
            <p className="text-lg text-muted-foreground leading-relaxed">{t('about.bio')}</p>
            <p className="text-lg text-muted-foreground leading-relaxed">{t('about.journey')}</p>
            <p className="text-lg text-muted-foreground leading-relaxed">{t('about.philosophy')}</p>
          </div>

          <div className="grid md:grid-cols-3 gap-4 mb-16">
            {infoItems.map((item, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: index * 0.1 }}
                className="p-4 rounded-xl card flex items-center gap-3"
              >
                <div className="p-2 rounded-lg bg-accent/10">
                  <item.icon className="w-5 h-5 text-accent" />
                </div>
                <span className="text-sm">{item.text}</span>
              </motion.div>
            ))}
          </div>
        </motion.div>

        <div>
          <h2 className="text-4xl font-bold font-mono mb-8 gradient-text">{t('about.skills')}</h2>
          <div className="grid md:grid-cols-2 gap-6">
            {skills.map((skill, index) => (
              <motion.div
                key={skill.title}
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: index * 0.1 + 0.3 }}
                className="p-6 rounded-2xl card hover:border-accent/30 transition-all duration-300"
              >
                <div className="flex items-center gap-4 mb-4">
                  <div className={`p-3 rounded-xl ${skill.color}`}>
                    <skill.icon className="w-6 h-6" />
                  </div>
                  <h3 className="text-2xl font-bold">{skill.title}</h3>
                </div>
                <div className="flex flex-wrap gap-2">
                  {skill.items.map((item) => (
                    <span
                      key={item}
                      className="px-3 py-1.5 rounded-lg card text-sm font-mono hover:bg-muted transition-all duration-200"
                    >
                      {item}
                    </span>
                  ))}
                </div>
              </motion.div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
